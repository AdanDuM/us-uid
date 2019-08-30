package net.douglashiura.us.serial;

import java.util.UUID;

public class Input extends AbstractType {

	private static final long serialVersionUID = 1L;

	public Input(UUID uuid, String fixtureName, String value) {
		super(uuid, fixtureName, value);
	}

}
