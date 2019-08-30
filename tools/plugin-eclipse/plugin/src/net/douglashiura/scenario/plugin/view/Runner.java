package net.douglashiura.scenario.plugin.view;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.internal.refresh.MonitorJob;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import net.douglashiura.scenario.plugin.editor.PaintScenario;
import net.douglashiura.scenario.plugin.editor.ScenarioView;
import net.douglashiura.scenario.plugin.editor.run.Executor;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.scenario.project.util.Files;
import net.douglashiura.us.serial.Results;

public class Runner {

	private static Runner action;
	private Executor executor;
	private PaintScenario paintScenario;
	private FileScenario current;
	protected ViewTests viewTests;
	private IContainer container;
	private ScenarioView scenario;

	private Runner() {
	}

	public static Runner getRunner() {
		if (action == null)
			action = new Runner();
		return action;
	}

	public void setContainer(IContainer container) {
		this.container = container;
	}

	public void run() {
		if (current != null) {
			execute(Arrays.asList(current), current.getProject());
		} else {
			MessageDialog.openInformation(null, "Fault", "It is necessary to open an scenario file!");
		}
	}

	public void runAll() {
		if (container != null) {
			try {
				List<FileScenario> nodes = Files.from(container);
				for (FileScenario fileScenario : nodes) {
					fileScenario.prepareToExecute();
				}
				execute(nodes, container.getProject());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			MessageDialog.openInformation(null, "Fault", "It is necessary to open an scenario folder!");
		}
	}

	private void execute(List<FileScenario> scenarios, IProject project) {
		if (current != null) {
			current.prepareToExecute();
		}
		viewTests.popularArvore(scenarios);
		executor = new Executor(scenarios);
		viewTests.setExecutionAmounts(executor);
		MonitorJob.create("Run as scenario test", new ICoreRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				monitor.worked(10);
				executor.execute(project, monitor);
				monitor.worked(100);
				monitor.done();
			}
		}).schedule();

	}

	public void addPaintScenario(PaintScenario paintScenario) {
		removePaintScenario();
		this.paintScenario = paintScenario;
		if (scenario != null && !scenario.getCanvas().isDisposed()) {
			scenario.getCanvas().addPaintListener(paintScenario);
			scenario.getCanvas().redraw();
		} else {
			MessageDialog.openInformation(null, "Fault", "It is necessary to open the scenario view!");
		}
	}

	private void removePaintScenario() {
		if (scenario != null && paintScenario != null && !scenario.getCanvas().isDisposed()) {
			scenario.getCanvas().removePaintListener(paintScenario);
		}
	}

	public void setCanvas(ScenarioView canvas) {
		this.scenario = canvas;
	}

	public void setCurrent(FileScenario scenario, Map<UUID, Rateable> neighbors) {
		this.current = scenario;
		try {
			removePaintScenario();
			addPaintScenario(new PaintScenario(current.getElementsCollection(), neighbors));
		} catch (NullPointerException error) {
			MessageDialog.openInformation(null, "Fault", "Could not open file");
		}
	}

	public FileScenario getCurrent() {
		return this.current;
	}

	public void redraw(Rateable rateable) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (scenario != null && !scenario.getCanvas().isDisposed()) {
					scenario.getCanvas().redraw();
					if (rateable != null) {
						setOrigin(rateable.getGeometry().getX(), rateable.getGeometry().getY());
					}
				}
			}
		});
	}

	public void updateStatusExecution(Results status) {
		executor.updateStatusExecution(status);
		viewTests.setExecutionAmounts(executor);
	}

	public void setViewTests(ViewTests viewTests) {
		this.viewTests = viewTests;
	}

	public void setOrigin(Integer x, Integer y) {
		if (scenario != null) {
			scenario.setOrigin(x, y);
		}
	}

}