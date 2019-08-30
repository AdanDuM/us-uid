package net.douglashiura.scenario.plugin.fixture.names;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.douglashiura.scenario.plugin.type.Geometry;
import net.douglashiura.scenario.plugin.view.Runner;
import net.douglashiura.scenario.project.Elementable;
import net.douglashiura.us.serial.Results;

public class Control {

	private TableCursor cursor;
	private Table table;
	private Integer columnEditable;
	private ControlEditor editor2;

	public Control(Table table, Integer columnEditable) {
		this.table = table;
		this.columnEditable = columnEditable;
		cursor = new TableCursor(table, SWT.NONE);
		cursor.addSelectionListener(new Selection());
		editor2 = new ControlEditor(cursor);
		editor2.grabHorizontal = true;
		editor2.grabVertical = true;
	}

	class Selection extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			table.setSelection(new TableItem[] { cursor.getRow() });
			TableItem item = cursor.getRow();
			Elementable element = (Elementable) item.getData();
			element.getFileScenario().prepareToExecute();
			element.getElement().setColor(Results.HIGHLIGHT.getColor());
			Runner.getRunner().setCurrent(element.getFileScenario(), element.getFileScenario().getElements());
			Geometry geometry = element.getElement().getGeometry();
			Runner.getRunner().setOrigin(geometry.getX(), geometry.getY());
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			int column = cursor.getColumn();
			if (column == columnEditable) {
				Text text = new Text(cursor, SWT.NONE);
				TableItem row = cursor.getRow();
				text.setText(row.getText(column));
				text.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						try {
							((Elementable) row.getData()).setFixtureName(text.getText());
						} catch (CoreException | IOException error) {
							error.printStackTrace();
						}
						int column = cursor.getColumn();
						if (e.character == SWT.CR && column == columnEditable) {
							TableItem row = cursor.getRow();
							row.setText(column, text.getText());
							text.dispose();
						}
						if (e.character == SWT.ESC) {
							text.dispose();
						}
					}
				});
				editor2.setEditor(text);
				text.setFocus();
			}
		}
	}

}
