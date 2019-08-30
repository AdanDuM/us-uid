package net.douglashiura.scenario.plugin.view;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

public class ActionRun extends Action {

	private static final String RUN_CURRENT = "Run Current";

	public ActionRun(ViewTests view) {
		setText(RUN_CURRENT);
		setToolTipText(RUN_CURRENT);
		setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(view.getContentDescription()));
	}

	@Override
	public void run() {
		Runner.getRunner().run();
	}

}
