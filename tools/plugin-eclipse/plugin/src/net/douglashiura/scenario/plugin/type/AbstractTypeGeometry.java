package net.douglashiura.scenario.plugin.type;

import java.util.UUID;

import com.google.gson.internal.LinkedTreeMap;

public abstract class AbstractTypeGeometry implements Rateable {

	private Geometry geometry;
	private String model;
	private String value;
	private UUID id;
	private LinkedTreeMap<String, ?> objectJson;

	public AbstractTypeGeometry(LinkedTreeMap<String, ?> objectJson, UUID id, Geometry geometry, String model, String value) {
		this.objectJson = objectJson;
		this.id = id;
		this.geometry = geometry;
		this.model = model;
		this.value = value;
	}

	abstract public String getSimpleType();

	public UUID getId() {
		return id;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public String getFixtureName() {
		return model;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	public void setFixtureName(String name) {
		Scenario.setFixtureName(name, objectJson);
	}
}
