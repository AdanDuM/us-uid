package net.douglashiura.leb.uid.scenario.ml.data;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.leb.uid.scenario.ml.data.Data.Type;
import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class DataFromUS {
	private List<Data> instances;
	private String scenario;
	private int interactionsRead;
	private int interactionsForRead;
	private int deep;

	private DataFromUS(Interaction first, String file) {
		this.scenario = file;
		this.instances = new ArrayList<>();
		this.interactionsRead = 0;
		this.interactionsForRead = deepOf(first, 0);
		this.deep = interactionsForRead;
		readInteraction(first);
	}

	private int deepOf(Interaction first, int count) {
		if (first.getTransaction() != null)
			deepOf(first.getTransaction().getTarget(), count++);
		return count;
	}

	private void readInteraction(Interaction first) {
		inputs(first);
		outputs(first);
		interactionsForRead--;
		interactionsRead++;
		if (first.getTransaction() != null)
			readInteraction(first.getTransaction().getTarget());
	}

	private void outputs(Interaction first) {
		List<Output> outputs = first.getOutputs();
		for (Output output : outputs) {
			Data instance = new Data();
			instance.setScenario(scenario);
			instance.setStartDistance(interactionsRead);
			instance.setEndDistance(interactionsForRead);
			instance.setDeep(deep);
			instance.setUuid(first.getUuid());
			instance.setInputs(first.getInputs().size());
			instance.setOutputs(first.getOutputs().size());
			instance.setElements(first.getInputs().size() + first.getOutputs().size());
			instance.setValue(output.getValue());
			String fixture = output.getFixtureName();
			fixture = fixture != null && fixture.isEmpty() ? null : fixture;
			instance.setFixture(fixture);
			instance.setType(Type.OUTPUT);
			instances.add(instance);
		}

	}

	private void inputs(Interaction first) {
		List<Input> inputs = first.getInputs();
		for (Input input : inputs) {
			Data instance = new Data();
			instance.setScenario(scenario);
			instance.setStartDistance(interactionsRead);
			instance.setEndDistance(interactionsForRead);
			instance.setDeep(deep);
			instance.setUuid(first.getUuid());
			instance.setInputs(first.getInputs().size());
			instance.setOutputs(first.getOutputs().size());
			instance.setElements(first.getInputs().size() + first.getOutputs().size());
			instance.setValue(input.getValue());
			String fixture = input.getFixtureName();
			fixture = fixture != null && fixture.isEmpty() ? null : fixture;
			instance.setFixture(fixture);
			instance.setType(Type.INPUT);
			instances.add(instance);
		}

	}

	public static List<Data> read(Interaction first, String file) {
		return new DataFromUS(first, file).instances;
	}

}
