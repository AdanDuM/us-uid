package net.douglashiura.scenario.plugin.type;

import java.awt.Color;
import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.graphics.GC;

public interface Rateable {

	void setColor(Color color);

	String getFixtureName();

	String getValue();

	void draw(GC gc, Map<UUID, Rateable> neighbors);

	Class<?> getType();

	Geometry getGeometry();

}