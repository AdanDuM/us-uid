package org.eclipse.swt.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.SWT;

import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.scenario.project.util.Notificable;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public class ItemPath extends TreeItem implements Notificable {

	private FileScenario scenario;
	private java.util.List<Result> results;
	private Integer indexPath;

	public ItemPath(ItemScenario item, Integer indexPath, FileScenario scenario) {
		super(item, SWT.NONE);
		this.indexPath = indexPath;
		this.scenario = scenario;
		setText(indexPath.toString());
		results = new ArrayList<Result>();
	}

	@Override
	public void addResult(Result result, Rateable element) {
		results.add(result);
		new ItemResult(ItemPath.this, element, result, scenario);
	}

	@Override
	public void finishyATestExecution(Results generalResult) {
		Results generalResult2 = checkGeneralResult();
		setText(String.format("%s (%s)", indexPath, generalResult2));
	}

	private Results checkGeneralResult() {
		for (Result result : results) {
			if (Results.ERROR.equals(result.getResult()) || Results.FAIL.equals(result.getResult())) {
				return result.getResult();
			}
		}
		return Results.OK;
	}

	public FileScenario getScenario() {
		return scenario;
	}

	public Map<UUID, Rateable> selected() {
		scenario.prepareToExecute();
		Map<UUID, Rateable> elements = scenario.getElements();
		Map<UUID, Rateable> neighbors = new HashMap<UUID, Rateable>();
		for (Result result : results) {
			Rateable rateable = elements.get(result.getUuid());
			rateable.setColor(result.getResult().getColor());
			neighbors.put(result.getUuid(), rateable);
		}
		return neighbors;
	}

}