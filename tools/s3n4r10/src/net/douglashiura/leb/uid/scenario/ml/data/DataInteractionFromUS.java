package net.douglashiura.leb.uid.scenario.ml.data;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.us.serial.Interaction;

public class DataInteractionFromUS {
	private List<DataInteraction> instances;
	private String scenario;
	private int interactionsRead;
	private int interactionsForRead;
	private int deep;

	private DataInteractionFromUS(Interaction first, String scenario) {
		this.scenario = scenario;
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
		DataInteraction instance = new DataInteraction();
		instance.setScenario(scenario);
		instance.setStartDistance(interactionsRead);
		instance.setEndDistance(interactionsForRead);
		instance.setDeep(deep);
		instance.setId(first.getUuid());
		String fixture = first.getFixtureName();
		fixture = fixture != null && fixture.isEmpty() ? null : fixture;
		instance.setFixture(fixture);
		instance.setInputs(first.getInputs().size());
		instance.setOutputs(first.getOutputs().size());
		instance.setElements(first.getInputs().size() + first.getOutputs().size());
		instances.add(instance);
		interactionsForRead--;
		interactionsRead++;
		if (first.getTransaction() != null)
			readInteraction(first.getTransaction().getTarget());
	}

	
	public static List<DataInteraction> read(Interaction first, String scenario) {
		return new DataInteractionFromUS(first,scenario).instances;
	}

}
