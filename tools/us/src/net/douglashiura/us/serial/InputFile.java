package net.douglashiura.us.serial;

import java.io.Serializable;

public class InputFile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String file;
	private Interaction scenario;

	private Integer index;

	public InputFile(String file, Interaction scenario, Integer index) {
		this.file = file;
		this.scenario = scenario;
		this.index = index;
	}

	public String getFile() {
		return file;
	}

	public Interaction getScenario() {
		return scenario;
	}

	public Integer getIndex() {
		return index;
	}

}
