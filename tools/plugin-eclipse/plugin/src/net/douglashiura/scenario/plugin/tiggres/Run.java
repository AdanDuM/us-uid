package net.douglashiura.scenario.plugin.tiggres;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;

import net.douglashiura.scenario.plugin.view.Runner;
import net.douglashiura.scenario.project.util.FileScenario;

public class Run implements org.eclipse.debug.ui.ILaunchShortcut {

	@Override
	public void launch(ISelection item, String modo) {
		TreeSelection type = (TreeSelection) item;
		Object selection = type.getFirstElement();
		if (selection instanceof org.eclipse.jdt.internal.core.JavaProject) {
			JavaProject java = (JavaProject) selection;
			Runner.getRunner().setContainer(java.getProject());
			Runner.getRunner().runAll();
		} else if (selection instanceof Project) {
			Project project = (Project) selection;
			Runner.getRunner().setContainer(project.getProject());
			Runner.getRunner().runAll();
		} else if (selection instanceof Folder) {
			Folder folder = (Folder) selection;
			Runner.getRunner().setContainer(folder);
			Runner.getRunner().runAll();
		} else if (selection instanceof File) {
			File file = (File) selection;
			try {
				FileScenario aFile = new FileScenario(file);
				Runner.getRunner().setCurrent(aFile, aFile.getElements());
				Runner.getRunner().run();
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void launch(IEditorPart arg0, String arg1) {
		throw new RuntimeException("Not implemented!");
	}

}