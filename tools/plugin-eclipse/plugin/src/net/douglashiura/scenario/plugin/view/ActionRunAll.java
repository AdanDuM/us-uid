package net.douglashiura.scenario.plugin.view;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

public class ActionRunAll extends Action {

	private static final String RUN_FOLDER = "Run All";

	public ActionRunAll(ViewTests view) {
		setText(RUN_FOLDER);
		setToolTipText(RUN_FOLDER);
		setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(view.getContentDescription()));
	}

	@Override
	public void run() {
		Runner.getRunner().runAll();

	}

}
