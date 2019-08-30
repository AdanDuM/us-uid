package net.douglashiura.scenario.plugin.view;

import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ItemPath;
import org.eclipse.swt.widgets.ItemResult;
import org.eclipse.swt.widgets.ItemScenario;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import net.douglashiura.scenario.plugin.type.Geometry;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;

public class TreeSelection implements Listener {

	private ViewTests view;

	public TreeSelection(ViewTests view) {
		this.view = view;
	}

	@Override
	public void handleEvent(Event event) {
		selectItemTree();
	}

	void selectItemTree() {
		TreeItem[] selectedItem = view.getTree().getSelection();
		if (selectedItem.length > 0)
			if (selectedItem[0] instanceof ItemScenario) {
				ItemScenario itemScenario = (ItemScenario) selectedItem[0];
				itemScenario.selected();
				FileScenario scenario = itemScenario.getScenario();
				Runner.getRunner().setCurrent(scenario, scenario.getElements());
				if (!scenario.getAllInteractions().isEmpty()) {
					InteractionGeometry interactionGeometry = scenario.getAllInteractions().get(0);
					Runner.getRunner().setOrigin(interactionGeometry.getGeometry().getX(),
							interactionGeometry.getGeometry().getY());
				}
			} else if (selectedItem[0] instanceof ItemResult) {
				ItemResult itemResult = (ItemResult) selectedItem[0];
				FileScenario scenario = itemResult.getScenario();
				Runner.getRunner().setCurrent(scenario, scenario.getElements());
				Rateable element = itemResult.getElement();
				view.setResult(itemResult.getResult(), element);
				Runner.getRunner().setOrigin(element.getGeometry().getX(), element.getGeometry().getY());
			} else if (selectedItem[0] instanceof ItemPath) {
				ItemPath result = (ItemPath) selectedItem[0];
				Map<UUID, Rateable> neighbors = result.selected();
				Runner.getRunner().setCurrent(result.getScenario(), neighbors);
				Geometry geometry = neighbors.values().iterator().next().getGeometry();
				Runner.getRunner().setOrigin(geometry.getX(), geometry.getY());
			}
	}
}
