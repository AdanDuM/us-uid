package net.douglashiura.leb.uid.scenario.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
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
		scenarios = new ArrayList<Scenario>(scenarios);
		Comparator<Scenario> comparator = new Comparator<Scenario>() {

			@Override
			public int compare(Scenario a, Scenario b) {
				try {
					return a.getVirtualName().compareTo(b.getVirtualName());
				} catch (NotAFileException e) {
					return 0;
				}
			}
		};
		Collections.sort(scenarios, comparator);
		return scenarios;
	}

	private void listScenariosRecursive(List<Scenario> scenarios, File directory) {
		for (File file : directory.listFiles(new FilterScenario())) {
			scenarios.add(new Scenario(file, workDirectoryOfProject, this));
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

	public Scenario createNewScenario(FileName fileName) throws IOException, DuplicationScenarioException {
		File directory = workDirectoryOfProject;
		for (String path : fileName.getPathsOfDirectory()) {
			directory = new File(directory, path);
			if (!directory.exists()) {
				directory.mkdir();
			}
		}
		File file = new File(directory, String.format("%s.us", fileName.getNameWithoutExtension()));
		if (file.exists()) {
			throw new DuplicationScenarioException();
		}
		Scenario scenario = new Scenario(file, workDirectoryOfProject, this);
		scenario.create();
		return scenario;
	}

	public Scenario getScenario(FileName fileName) throws FileNotFoundException {
		File directory = workDirectoryOfProject;
		for (String path : fileName.getPathsOfDirectory()) {
			directory = new File(directory, path);
		}
		File file = new File(directory, String.format("%s.us", fileName.getNameWithoutExtension()));
		if (file.exists() && file.isFile()) {
			return new Scenario(file, workDirectoryOfProject, this);
		} else {
			throw new FileNotFoundException();
		}
	}

	public void delete() throws IOException {
		if (!workDirectoryOfProject.delete() && listScenarios().isEmpty()) {
			FileUtils.forceDelete(workDirectoryOfProject);
		}
	}

	public void rename(SimpleName name) throws ProjectUnavailableException {
		File dest = new File(workDirectoryOfProject.getParent(), name.getName());
		if (dest.exists()) {
			throw new ProjectUnavailableException();
		}
		workDirectoryOfProject.renameTo(dest);

	}

}
