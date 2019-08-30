package net.douglashiura.leb.uid.scenario.ml.data;

import java.util.UUID;

public class DataInteraction {
	
	private UUID id;
	private String scenario;
	private String fixture;
	private Integer startDistance;
	private Integer endDistance;
	private Integer deep;
	private Integer elemets;
	private Integer inputs;
	private Integer outputs;

	public String getFixture() {
		return fixture;
	}

	public void setFixture(String fixture) {
		this.fixture = fixture;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer size) {
		this.deep = size;
	}

	public Integer getElements() {
		return elemets;
	}

	public void setElements(Integer elements) {
		this.elemets = elements;
	}

}
