package net.douglashiura.leb.uid.scenario.measures.uniform;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Output;

class Counter {
	private List<Input> uniformInputs;
	private List<Output> uniformOutputs;

	public Counter(ElementsCluster clusterA, ElementsCluster clusterB) {
		uniformInputs = new ArrayList<>();
		uniformOutputs = new ArrayList<>();
		compareInputs(clusterA, clusterB);
		compareOutputs(clusterA, clusterB);
	}

	private void compareInputs(ElementsCluster clusterA, ElementsCluster clusterB) {
		for (Input input : clusterA.getInputs()) {
			compareData(input, clusterB);
		}
	}

	private void compareOutputs(ElementsCluster clusterA, ElementsCluster clusterB) {
		for (Output output : clusterA.getOutputs()) {
			compareData(output, clusterB);
		}
	}

	private void compareData(Input input, ElementsCluster clusterB) {
		for (Input anInput : clusterB.getInputs()) {
			if (input.getValue().trim().equals(anInput.getValue().trim())) {
				uniformInputs.add(input);
				return;
			}
		}
	}

	private void compareData(Output output, ElementsCluster clusterB) {
		for (Output anOutput : clusterB.getOutputs()) {
			if (output.getValue().trim().equals(anOutput.getValue().trim())) {
				uniformOutputs.add(output);
				return;
			}
		}
	}

	public List<Output> getUniformOutputs() {
		return uniformOutputs;
	}

	public List<Input> getUniformInputs() {
		return uniformInputs;
	}
}