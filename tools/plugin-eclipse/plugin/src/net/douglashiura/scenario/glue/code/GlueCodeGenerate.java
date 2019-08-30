package net.douglashiura.scenario.glue.code;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.douglashiura.us.serial.Interaction;

public class GlueCodeGenerate {

	private GlueCodeOfScenarioSet codeOfScenarioSet;
	private IProject project;

	public GlueCodeGenerate(String _package, IProject project) throws IOException, CoreException {
		this(_package, (IContainer) project.getProject());
		this.project = project;
	}

	private GlueCodeGenerate(String _package, IContainer directory) throws IOException, CoreException {
		GetAllScenariosOfAPath allScenariosOfAPath = new GetAllScenariosOfAPath(directory);
		List<Interaction> scenarios = ExtractInteractions.from(allScenariosOfAPath.getAll());
		codeOfScenarioSet = new GlueCodeOfScenarioSet(_package, scenarios);
	}

	@Override
	public String toString() {
		List<ClassTemplate> classes = codeOfScenarioSet.getClasses();
		String code = new String();
		for (ClassTemplate classTemplate : classes) {
			code += classTemplate.toString() + "\n";
		}
		return code;
	}

	public void write() throws CoreException {
		IProject aProject = project.getProject();
		List<ClassTemplate> classes = codeOfScenarioSet.getClasses();
		for (ClassTemplate classTemplate : classes) {
			createFolder(classTemplate.getPackage(File.separator));
			IFile file = aProject.getFile(String.format("src%s%s%s%s.java", File.separator,
					classTemplate.getPackage(File.separator), File.separator, classTemplate.getName()));
			InputStream source = new ByteArrayInputStream(classTemplate.toString().getBytes());
			if (!file.exists()) {
				file.create(source, false, null);
			}

		}

	}

	private void createFolder(String package1) throws CoreException {
		String[] packes = package1.split(File.separator);
		String paths = "src";
		for (String pack : packes) {
			paths += File.separator + pack;
			IFolder src = project.getProject().getFolder(paths);
			if (!src.exists()) {
				src.create(true, false, new NullProgressMonitor());
			}

		}
	}

}
