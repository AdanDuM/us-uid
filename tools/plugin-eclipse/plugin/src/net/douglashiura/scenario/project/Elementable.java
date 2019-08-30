package net.douglashiura.scenario.project;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;

public interface Elementable {
	public void setFixtureName(String text) throws CoreException, IOException;

	public FileScenario getFileScenario();

	public Rateable getElement();

}
