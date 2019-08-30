package net.douglashiura.scenario.plugin.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;

import net.douglashiura.scenario.plugin.view.Runner;

public class ScenarioView extends ViewPart {

	private Canvas canvas;
	private ScrolledComposite uid;

	public void createPartControl(Composite parent) {
		uid = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		canvas = new Canvas(uid, SWT.NONE);
		canvas.setSize(10000, 10000);
		uid.setContent(canvas);
		uid.pack();
		Runner.getRunner().setCanvas(this);

	}

	public void setFocus() {
	}

	public Control getCanvas() {
		return canvas;
	}

	public void setOrigin(Integer x, Integer y) {
		uid.setOrigin(x-300, y-300);
	}
}