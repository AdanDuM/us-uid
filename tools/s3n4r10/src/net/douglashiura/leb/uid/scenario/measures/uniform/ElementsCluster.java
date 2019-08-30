package net.douglashiura.leb.uid.scenario.measures.uniform;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.leb.uid.scenario.model.InteractionTree;
import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Output;

public class ElementsCluster {
	private List<Input> inputs;
	private List<Output> outputs;

	public ElementsCluster(List<InteractionTree> scenarioA) {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		for (InteractionTree interactionTree : scenarioA) {
			readAll(interactionTree);
		}
	}

	private void readAll(InteractionTree scenario) {
		inputs.addAll(scenario.getInputs());
		outputs.addAll(scenario.getOutputs());

	}

	public List<Input> getInputs() {
		return inputs;
	}

	public List<Output> getOutputs() {
		return outputs;
	}

}
