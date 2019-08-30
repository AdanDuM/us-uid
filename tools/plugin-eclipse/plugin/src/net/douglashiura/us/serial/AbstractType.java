package net.douglashiura.us.serial;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractType implements Serializable, Elementable {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private String fixtureName;
	private String value;

	public AbstractType(UUID uuid, String fixtureName, String value) {
		this.uuid = uuid;
		this.fixtureName = fixtureName;
		this.value = value;
	}

	@Override
	public UUID getUuid() {
		return uuid;
	}

	public String getFixtureName() {
		return fixtureName;
	}

	public String getValue() {
		return value;
	}

}
