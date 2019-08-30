package net.douglashiura.scenario.plugin.tiggres;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShortCutHandle extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		TreeSelection aEvent = (TreeSelection) HandlerUtil.getCurrentSelection(event);
		new Run().launch(aEvent, "Run");
		return null;
	}
}