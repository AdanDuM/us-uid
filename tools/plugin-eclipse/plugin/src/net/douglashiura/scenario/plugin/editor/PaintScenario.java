package net.douglashiura.scenario.plugin.editor;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

import net.douglashiura.scenario.plugin.type.Rateable;

public class PaintScenario implements PaintListener {
	private Collection<Rateable> scenario;
	private Map<UUID, Rateable> neighbors;

	public PaintScenario(Collection<Rateable> states, Map<UUID, Rateable> neighbors) {
		this.scenario = states;
		this.neighbors = neighbors;
	}

	@Override
	public void paintControl(PaintEvent e) {
		for (Rateable element : scenario) {
			element.draw(e.gc, neighbors);
		}
	}
}
