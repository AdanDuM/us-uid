package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;

import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.plugin.type.TransactionGeometry;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.us.serial.Result;

public class ItemResult extends TreeItem {

	private Rateable element;
	private Result result;
	private FileScenario fileScenario;

	public ItemResult(ItemScenario item, Rateable element, Result result, FileScenario scenario) {
		this((TreeItem) item, element, result, scenario);
	}

	public ItemResult(ItemPath item, Rateable element, Result result, FileScenario scenario) {
		this((TreeItem) item, element, result, scenario);
	}

	private ItemResult(TreeItem item, Rateable element, Result result,FileScenario fileScenario) {
		super(item, SWT.NONE);
		this.element = element;
		this.result = result;
		this.fileScenario = fileScenario;
		String identification = "";
		if (InteractionGeometry.class.equals(element.getType())
				|| TransactionGeometry.class.equals(element.getType())) {
			identification = element.getFixtureName();
		} else {
			identification = element.getValue();
		}
		setText(String.format("(%s) %s", result.getResult(), identification));
	}

	public Rateable getElement() {
		return element;
	}

	public Result getResult() {
		return result;
	}

	public FileScenario getScenario() {
		return fileScenario;
	}

}