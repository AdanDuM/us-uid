package net.douglashiura.scenario.plugin.type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.internal.LinkedTreeMap;

public class InteractionGeometry extends DrawInteraction implements Rateable {
	private String model;
	private Geometry geometry;
	private TransactionGeometry transaction;
	private List<InputGeometry> inputs;
	private List<OutputGeometry> outputs;
	private UUID id;
	private LinkedTreeMap<String, ?> objectJson;

	public InteractionGeometry(LinkedTreeMap<String, ?> objectJson, UUID id, Geometry geometry, String model) {
		this.objectJson = objectJson;
		this.id = id;
		this.geometry = geometry;
		this.model = model;
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public String getFixtureName() {
		return model;
	}

	public TransactionGeometry getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionGeometry aTarget) {
		this.transaction = aTarget;
	}

	public List<InputGeometry> getInputs() {
		return inputs;
	}

	public void addInput(InputGeometry input) {
		inputs.add(input);

	}

	public List<OutputGeometry> getOutputs() {
		return outputs;
	}

	public void addOutput(OutputGeometry output) {
		outputs.add(output);
	}

	public UUID getId() {
		return id;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public Class<?> getType() {
		return InteractionGeometry.class;
	}

	public void setFixtureName(String name) {
		Scenario.setFixtureName(name, objectJson);
	}

}
