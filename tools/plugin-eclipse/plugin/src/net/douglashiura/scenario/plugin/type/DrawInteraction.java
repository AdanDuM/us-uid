package net.douglashiura.scenario.plugin.type;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public abstract class DrawInteraction implements Rateable {
	private java.awt.Color color;

	public DrawInteraction() {
		color = java.awt.Color.BLACK;
	}

	abstract List<InputGeometry> getInputs();

	public abstract Geometry getGeometry();

	abstract List<OutputGeometry> getOutputs();

	abstract TransactionGeometry getTransaction();

	@Override
	public void draw(GC gc, Map<UUID, Rateable> neighbors) {
		Color aColor = new Color(gc.getDevice(), color.getRed(), color.getGreen(), color.getBlue());
		gc.setForeground(aColor);
		Geometry geometry = getGeometry();
		gc.drawOval(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight());
	}

	@Override
	public void setColor(java.awt.Color color) {
		this.color = color;
	}
}
