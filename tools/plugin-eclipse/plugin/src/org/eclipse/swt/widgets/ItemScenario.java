package org.eclipse.swt.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.eclipse.swt.SWT;

import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.scenario.project.util.Notificable;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public class ItemScenario extends TreeItem implements Notificable {

	private FileScenario scenario;
	private Map<Integer, ItemPath> paths;
	private List<Result> results;

	public ItemScenario(Tree arvoreDeElementos, FileScenario aScenario) {
		super(arvoreDeElementos, SWT.NONE);
		this.scenario = aScenario;
		this.results = new ArrayList<Result>();
		setText(aScenario.getName());
		if (aScenario.getPaths().size() > 1) {
			paths = new HashMap<>();
		}
	}

	public FileScenario getScenario() {
		return scenario;
	}

	@Override
	public void addResult(Result result, Rateable element) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				if (scenario.getPaths().size() > 1) {
					ItemPath item = getPath(result);
					item.addResult(result, element);
				} else {
					new ItemResult(ItemScenario.this, element, result, scenario);
				}
				results.add(result);
			}

			private ItemPath getPath(Result result) {
				ItemPath path = paths.get(result.getIndex());
				if (path == null) {
					path = new ItemPath(ItemScenario.this, result.getIndex(), scenario);
					paths.put(result.getIndex(), path);
				}
				return path;
			}
		});
	}

	public void prepareToExecute() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				removeAll();
				setText(scenario.getName());
				scenario.prepareToExecute();
			}
		});
	}

	@Override
	public void finishyATestExecution(Results generalResult) {

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (!isDisposed()) {
					setText(String.format("%s (%s)", scenario.getName(), getContextResult()));
					if (paths != null) {
						for (Entry<Integer, ItemPath> item : paths.entrySet()) {
							item.getValue().finishyATestExecution(generalResult);
						}
					}
				}
			}

			private Results getContextResult() {
				for (Result result : results) {
					if (Results.ERROR.equals(result.getResult()) || Results.FAIL.equals(result.getResult())) {
						return result.getResult();
					}
				}
				return Results.OK;
			}
		});
	}

	public void selected() {
		Map<UUID, Rateable> elements = scenario.getElements();
		for (Result result : results) {
			Rateable rateable = elements.get(result.getUuid());
			rateable.setColor(result.getResult().getColor());
		}

	}
}