package net.douglashiura.scenario.plugin.fixture.names;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.douglashiura.scenario.project.Element;
import net.douglashiura.scenario.project.Elements;

public class InputOutputView implements ViewControlable {

	private Table tableInputsOutputs;
	private List<Element> elements;
	private Group group;

	public InputOutputView(Group group) {
		this.group = group;
		group.setLayout(new GridLayout(1, false));
		group.setText("Inputs and outputs");
	}

	@Override
	public void createTable(List<IJavaProject> projects) {
		tableInputsOutputs = new Table(group, SWT.BORDER | SWT.RESIZE | SWT.SCROLL_PAGE | SWT.V_SCROLL | SWT.H_SCROLL);
		tableInputsOutputs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableInputsOutputs.setHeaderVisible(true);
		tableInputsOutputs.setLinesVisible(true);
		TableColumn file = new TableColumn(tableInputsOutputs, SWT.NONE);
		file.setWidth(219);
		file.setText("File");
		TableColumn fixtureName = new TableColumn(tableInputsOutputs, SWT.NONE);
		fixtureName.setWidth(246);
		fixtureName.setText("Fixture name (click to auto naming)");
		SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					autoNamingFixtures();
					createItens(projects);
				} catch (CoreException  e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		};
		fixtureName.addSelectionListener(listener);
		TableColumn value = new TableColumn(tableInputsOutputs, SWT.NONE);
		value.setWidth(246);
		value.setText("Value");
		TableColumn interaction = new TableColumn(tableInputsOutputs, SWT.NONE);
		interaction.setWidth(246);
		interaction.setText("Interaction fixture name");
		TableColumn type = new TableColumn(tableInputsOutputs, SWT.NONE);
		type.setWidth(100);
		type.setText("Type");
		createItens(projects);
		new Control(tableInputsOutputs, 1);
	}

	@Override
	public void createItens(List<IJavaProject> projects) {
		tableInputsOutputs.removeAll();
		try {
			elements = Elements.ofInputsAndOutputsFrom(projects);
			for (Element element : elements) {
				TableItem item = new TableItem(tableInputsOutputs, SWT.NONE);
				item.setText(0, element.getFile());
				item.setText(1, element.getFixtureName());
				item.setText(2, element.getValue());
				item.setText(3, element.getInteractionFixtureName());
				item.setText(4, element.getType());
				item.setData(element);
			}
		} catch (CoreException | IOException e1) {
			e1.printStackTrace();
		}
	}

	public void autoNamingFixtures() throws CoreException {
		for (Element element : elements) {
			findNameFor(element);
		}
	}

	private void findNameFor(Element element) throws CoreException {
		for (Element element2 : elements) {
			if (named(element2) && !named(element) && sameValue(element, element2)) {
				element.setFixtureName(element2.getFixtureName());
			}
		}
	}

	private boolean sameValue(Element element, Element element2) {
		return element.getValue() != null && element.getValue().equalsIgnoreCase(element2.getValue());
	}

	private boolean named(Element element2) {
		return element2.getFixtureName() != null && !element2.getFixtureName().isEmpty();
	}

}