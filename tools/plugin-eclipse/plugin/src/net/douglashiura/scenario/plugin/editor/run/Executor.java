package net.douglashiura.scenario.plugin.editor.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import net.douglashiura.scenario.plugin.editor.PathScenario;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.us.serial.InputFile;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public class Executor {

	private Integer complete;
	private Integer faults;
	private Integer errors;
	private PathScenario scenarioProcessing;
	private Iterator<PathScenario> scenarios;
	private Integer total;
	private Processor processor;
	private Results last;

	public Executor(List<FileScenario> scenarios) {
		List<PathScenario> manyPaths = new ArrayList<PathScenario>();
		for (FileScenario fileScenario : scenarios) {
			List<Interaction> paths = fileScenario.getPaths();
			Integer index = 0;
			for (Interaction scenario : paths) {
				manyPaths.add(new PathScenario(fileScenario, scenario, index++));
			}
		}
		this.total = manyPaths.size();
		this.scenarios = manyPaths.iterator();
		prepareExeution();
	}

	public void execute(IProject project, IProgressMonitor monitor) throws CoreException {
		processor = new Processor(project, this, monitor);
		next();
	}

	private void next() throws CoreException {
		if (scenarios.hasNext()) {
			PathScenario lastScenario = scenarioProcessing;
			scenarioProcessing = scenarios.next();
			if (!(isSame(lastScenario, scenarioProcessing) && lastIsErro())) {
				FileScenario fileScenario = scenarioProcessing.getFileScenario();
				fileScenario.prepareToExecute();
				try {
					processor.write(new InputFile(fileScenario.getName(), scenarioProcessing.getScenario(),
							scenarioProcessing.getIndex()));
				} catch (IOException e) {
					IStatus status = new Status(0, "net.douglashiura.scenario.plugin.editor.ScenarioView",
							e.getMessage(), e);
					throw new CoreException(status);
				}
			} else if (!isSame(lastScenario, scenarioProcessing)) {
				last = null;
				delivery(new Result(null, scenarioProcessing.getIndex(), Results.FAIL, "unavaliable"));
			}else {
				delivery(new Result(null, scenarioProcessing.getIndex(), Results.FAIL, "unavaliable"));
			}
		} else {
			processor.exit();
		}
	}

	private boolean isSame(PathScenario lastScenario, PathScenario scenarioProcessing) {
		return lastScenario != null && scenarioProcessing != null
				&& lastScenario.getFileScenario().getName().equals(scenarioProcessing.getFileScenario().getName());
	}

	private boolean lastIsErro() {
		return (Results.ERROR.equals(last) || Results.FAIL.equals(last));
	}

	public void delivery(Result result) {
		this.last = result.getResult();
		scenarioProcessing.getFileScenario().addResult(result);
		if (Results.isExecutionFinishy(result.getResult())) {
			try {
				next();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	private void prepareExeution() {
		complete = 0;
		faults = 0;
		errors = 0;
	}

	public Integer getComplete() {
		return complete;
	}

	public Integer getErrors() {
		return errors;
	}

	public Integer getFaults() {
		return faults;
	}

	public Integer getTotal() {
		return total;
	}

	public void updateStatusExecution(Results status) {
		complete++;
		if (Results.ERROR.equals(status))
			errors++;
		else if (Results.FAIL.equals(status))
			faults++;
	}

}