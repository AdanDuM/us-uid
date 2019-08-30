package net.douglashiura.leb.uid.scenario.ml.data;

import java.util.UUID;

public class Data {
	private String scenario;
	private UUID uuid;
	private String parent;
	private Type type;
	private String value;
	private String fixture;
	private Integer startDistance;
	private Integer endDistance;
	private Integer deep;
	private Integer inputs;
	private Integer outputs;
	private Integer elements;

	public enum Type {
		INPUT, OUTPUT
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFixture() {
		return fixture;
	}

	public void setFixture(String fixture) {
		this.fixture = fixture;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID id) {
		this.uuid = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Integer getStartDistance() {
		return startDistance;
	}

	public void setStartDistance(Integer startDistance) {
		this.startDistance = startDistance;
	}

	public Integer getEndDistance() {
		return endDistance;
	}

	public void setEndDistance(Integer endDistance) {
		this.endDistance = endDistance;
	}

	public Integer getInputs() {
		return inputs;
	}

	public void setInputs(Integer inputs) {
		this.inputs = inputs;
	}

	public Integer getOutputs() {
		return outputs;
	}

	public void setOutputs(Integer outputs) {
		this.outputs = outputs;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public Integer getElements() {
		return elements;
	}

	public void setElements(Integer elements) {
		this.elements = elements;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

}
