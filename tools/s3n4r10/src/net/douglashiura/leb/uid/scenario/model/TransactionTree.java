package net.douglashiura.leb.uid.scenario.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionTree {

	private List<InteractionTree> targets;

	public TransactionTree(UUID extractId, InteractionTree aSource) {
		targets = new ArrayList<InteractionTree>();
	}

	public void addTarget(InteractionTree aTarget) {
		targets.add(aTarget);
	}

	public List<InteractionTree> getTargets() {
		return targets;
	}

}
