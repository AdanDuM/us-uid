package net.douglashiura.scenario.plugin.type;

public class InteractionAction {

	private InteractionGeometry target;
	private String action;

	public InteractionAction(InteractionGeometry target, String action) {
		this.target = target;
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public InteractionGeometry getTarget() {
		return target;
	}
}
