package net.douglashiura.leb.uid.scenario.measures.uniform;

import java.util.List;

import net.douglashiura.leb.uid.scenario.model.InteractionTree;

public class AbsoluteUniformity {
	private ElementsCluster clusterA;
	private RelativeUniformity relative;

	public AbsoluteUniformity(List<InteractionTree> scenarioA, List<InteractionTree> scenarioB) {
		clusterA = new ElementsCluster(scenarioA);
		ElementsCluster clusterB = new ElementsCluster(scenarioB);
		Counter counter = new Counter(clusterA, clusterB);
		Integer uniformOutputs = counter.getUniformOutputs().size();
		Integer uniformIntputs = counter.getUniformInputs().size();
		Integer nonUniformInputs = getInputs() - uniformIntputs;
		Integer nonUniformOutputs = getOutputs() - uniformOutputs;
		relative = new RelativeUniformity();
		relative.setNonUniformInput(nonUniformInputs);
		relative.setNonUniformOutput(nonUniformOutputs);
		relative.setUniformInput(uniformIntputs);
		relative.setUniformOutput(uniformOutputs);
	}

	public RelativeUniformity getRelativeUniformity() {
		return relative;
	}

	public Integer getInputs() {
		return clusterA.getInputs().size();
	}

	public Integer getOutputs() {
		return clusterA.getOutputs().size();
	}

	public Integer getUniformIntputs() {
		return relative.getUniformInputs();
	}

	public Integer getUniformOutputs() {
		return relative.getUniformOutputs();
	}

	public Integer getNonUniforInputs() {
		return relative.getNonUniformInputs();
	}

	public Integer getNonUniformOutputs() {
		return relative.getNonUniformOutputs();
	}
}