package net.douglashiura.scenario.project;

import java.util.List;

import org.eclipse.core.runtime.CoreException;

import net.douglashiura.scenario.plugin.type.InputGeometry;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.OutputGeometry;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;

public class FileInteraction implements Elementable {

	private FileScenario fileScenario;
	private InteractionGeometry interaction;

	public FileInteraction(FileScenario fileScenario, InteractionGeometry interaction) {
		this.fileScenario = fileScenario;
		this.interaction = interaction;
	}

	public String getFile() {
		return fileScenario.getName();
	}

	public String getFixtureName() {
		return interaction.getFixtureName();
	}

	public List<InputGeometry> getInputs() {
		return interaction.getInputs();
	}

	public List<OutputGeometry> getOutputs() {
		return interaction.getOutputs();
	}

	@Override
	public void setFixtureName(String text) throws CoreException {
		interaction.setFixtureName(text);
		fileScenario.save();
	}

	@Override
	public FileScenario getFileScenario() {
		return fileScenario;
	}

	@Override
	public Rateable getElement() {
		return interaction;
	}

}
