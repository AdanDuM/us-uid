package net.douglashiura.scenario.plugin.fixture.names;

import java.util.List;

import org.eclipse.core.internal.refresh.MonitorJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.douglashiura.scenario.project.Elements;
import net.douglashiura.scenario.project.FileInteraction;
import net.douglashiura.scenario.project.util.AutoNameByUniformity;

public class InteractionView implements ViewControlable {

	private Table tableInteractions;
	private Group group;
	private List<FileInteraction> elements;

	public InteractionView(Group group) {
		this.group = group;
		group.setLayout(new GridLayout(1, false));
		group.setText("Interactions");
	}

	@Override
	public void createTable(List<IJavaProject> projects) throws CoreException {
		tableInteractions = new Table(group, SWT.BORDER | SWT.RESIZE | SWT.SCROLL_PAGE | SWT.V_SCROLL | SWT.H_SCROLL);
		tableInteractions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableInteractions.setHeaderVisible(true);
		tableInteractions.setLinesVisible(true);
		TableColumn file = new TableColumn(tableInteractions, SWT.NONE);
		file.setWidth(219);
		file.setText("File");
		TableColumn fixtureName = new TableColumn(tableInteractions, SWT.NONE);
		fixtureName.setWidth(246);
		fixtureName.setText("Fixture name (click to auto naming)");
		SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MonitorJob.create("Naming fixtures", new ICoreRunnable() {
					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						monitor.worked(10);
						autoNamingFixtures();
						monitor.worked(50);

						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								try {
									createItens(projects);
								} catch (CoreException e) {
									e.printStackTrace();
								}
							}
						});
						monitor.worked(100);
						monitor.done();
					}
				}).schedule();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
		fixtureName.addSelectionListener(listener);
		TableColumn inputs = new TableColumn(tableInteractions, SWT.NONE);
		inputs.setWidth(246);
		inputs.setText("Inputs");
		TableColumn outputs = new TableColumn(tableInteractions, SWT.NONE);
		outputs.setWidth(246);
		outputs.setText("Ouputs");
		createItens(projects);
		new Control(tableInteractions, 1);
	}

	@Override
	public void createItens(List<IJavaProject> projects) throws CoreException {
		tableInteractions.removeAll();
		elements = Elements.ofInteractionsFrom(projects);
		for (FileInteraction element : elements) {
			TableItem item = new TableItem(tableInteractions, SWT.NONE);
			item.setText(0, element.getFile());
			item.setText(1, element.getFixtureName());
			item.setText(2, element.getInputs().toString());
			item.setText(3, element.getOutputs().toString());
			item.setData(element);
		}

	}

	private void autoNamingFixtures() throws CoreException {
		new AutoNameByUniformity(elements).naming();
	}

}
