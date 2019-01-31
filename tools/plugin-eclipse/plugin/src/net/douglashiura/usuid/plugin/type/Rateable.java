package net.douglashiura.usuid.plugin.type;

import java.awt.Color;

import org.eclipse.swt.graphics.GC;

public interface Rateable {

	void rate(Color color);

	String getFixtureName();

	String getValue();

	public void draw(GC gc);

	Class<?> getType();

}