package net.douglashiura.scenario.plugin.fixture.names;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;

public interface ViewControlable {

	void createTable(List<IJavaProject> projects) throws CoreException;

	void createItens(List<IJavaProject> projects) throws CoreException;

}
