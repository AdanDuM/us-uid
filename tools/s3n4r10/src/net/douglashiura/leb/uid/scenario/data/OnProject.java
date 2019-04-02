package net.douglashiura.leb.uid.scenario.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.douglashiura.leb.uid.scenario.servlet.util.FileName;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

public class OnProject {

	private File workDirectoryOfProject;

	OnProject(File workDirectoryOfProject) {
		this.workDirectoryOfProject = workDirectoryOfProject;
	}

	public List<Scenario> listScenarios() {
		List<Scenario> scenarios = new LinkedList<Scenario>();
		listScenariosRecursive(scenarios, workDirectoryOfProject);
		return scenarios;
	}

	private void listScenariosRecursive(List<Scenario> scenarios, File directory) {
		for (File file : directory.listFiles(new FilterScenario())) {
			scenarios.add(new Scenario(file, workDirectoryOfProject));
		}
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				listScenariosRecursive(scenarios, file);
			}
		}
	}

	public List<String> listScenariosAsNames() throws IOException {
		List<Scenario> scenarios = listScenarios();
		List<String> manyScenarios = new ArrayList<String>(scenarios.size());
		for (Scenario file : scenarios) {
			try {
				manyScenarios.add(file.getVirtualName());
			} catch (NotAFileException neverTouchHereItIsChecked) {
				neverTouchHereItIsChecked.printStackTrace();
			}
		}
		return manyScenarios;
	}

	public Scenario createNewScenario(FileName fileName) throws IOException {
		File directory = workDirectoryOfProject;
		for (String path : fileName.getPathsOfDirectory()) {
			directory = new File(directory, path);
			if (!directory.exists()) {
				directory.mkdir();
			}
		}
		File file = new File(directory, String.format("%s.us", fileName.getNameWithoutExtension()));
		Scenario scenario = new Scenario(file, workDirectoryOfProject);
		scenario.create();
		return scenario;
	}

//	public void rename(FileUtil file) throws IOException {
//		byte[] data = getScenario().getDocument().getBytes();
//		delete();
//		Project.get(file.getDirectory()).newScenario(file.getNameWithoutExtension()).write(data);
//	}
//
//	public void clone(FileUtil file) throws IOException {
//		byte[] data = getScenario().getDocument().getBytes();
//		Project.get(file.getDirectory()).newScenario(file.getNameWithoutExtension()).write(data);
//	}
}