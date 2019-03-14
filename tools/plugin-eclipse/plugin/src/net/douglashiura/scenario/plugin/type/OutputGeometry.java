package net.douglashiura.scenario.plugin.type;

import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import com.google.gson.internal.LinkedTreeMap;

public class OutputGeometry extends AbstractTypeGeometry {
	private java.awt.Color color;

	public OutputGeometry(LinkedTreeMap<String, ?> objectJson, UUID id, Geometry geometry, String model, String value) {
		super(objectJson, id, geometry, model, value);
		color = java.awt.Color.BLACK;
	}

	public void draw(GC gc, Map<UUID, Rateable> neighbors) {
		Color aColor = new Color(gc.getDevice(), color.getRed(), color.getGreen(), color.getBlue());
		gc.setForeground(aColor);
		Geometry geometry = getGeometry();
		gc.drawText(getValue(), geometry.getX() + 3, geometry.getY() + 3);
	}

	@Override
	public void setColor(java.awt.Color cor) {
		this.color = cor;
	}

	@Override
	public Class<?> getType() {
		return OutputGeometry.class;
	}

	@Override
	public String getSimpleType() {
		return "Output";
	}

}