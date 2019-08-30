package net.douglashiura.scenario.plugin.editor;

import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.us.serial.Interaction;

public class PathScenario {

	private FileScenario fileScenario;
	private Interaction scenario;
	private Integer index;

	public PathScenario(FileScenario fileScenario, Interaction scenario, Integer index) {
		this.fileScenario = fileScenario;
		this.scenario = scenario;
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public FileScenario getFileScenario() {
		return fileScenario;
	}

	public Interaction getScenario() {
		return scenario;
	}
}
