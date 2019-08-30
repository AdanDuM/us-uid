package net.douglashiura.us;

import java.util.UUID;

import net.douglashiura.us.serial.Results;

public class ExceptionInExecution extends Exception {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private Results error;
	private String message;

	public ExceptionInExecution(UUID uuid, Results error, String message) {
		this.uuid = uuid;
		this.error = error;
		this.message = message;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Results getResult() {
		return error;
	}

	public String getMessage() {
		return message;
	}

}
