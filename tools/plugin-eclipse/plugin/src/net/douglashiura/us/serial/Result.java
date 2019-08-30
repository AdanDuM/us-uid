package net.douglashiura.us.serial;

import java.io.Serializable;
import java.util.UUID;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String SPLIT = "<##SPLIT##>";
	private UUID uuid;
	private Results result;
	private String actual;
	private Integer index;

	public Result(UUID uuid, Integer index, Results result, String actual) {
		this.uuid = uuid;
		this.index = index;
		this.result = result;
		this.actual = actual;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Results getResult() {
		return result;
	}

	public String getActual() {
		return actual;
	}

	public Integer getIndex() {
		return index;
	}

}
