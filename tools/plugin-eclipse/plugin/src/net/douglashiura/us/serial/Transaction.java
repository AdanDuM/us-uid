package net.douglashiura.us.serial;

import java.io.Serializable;
import java.util.UUID;

public class Transaction implements Serializable, Elementable {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private Interaction target;
	private String action;

	public Transaction(UUID uuid, Interaction source, Interaction target, String action) {
		this.uuid = uuid;
		this.target = target;
		this.action = action;
	}

	public Interaction getTarget() {
		return target;
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public String getAction() {
		return action;
	}

}
