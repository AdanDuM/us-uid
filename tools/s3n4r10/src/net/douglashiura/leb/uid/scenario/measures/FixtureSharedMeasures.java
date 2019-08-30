package net.douglashiura.leb.uid.scenario.measures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class FixtureSharedMeasures {

	private List<Interaction> states;
	private List<Input> inputs;
	private List<Output> outputs;
	private Map<String, List<Output>> outputsMap;
	private Map<String, List<Input>> inputsMap;
	private Map<String, List<Interaction>> statesMap;

	public FixtureSharedMeasures(List<Interaction> scenarios) {
		states = new ArrayList<>();
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		statesMap = new HashMap<>();
		inputsMap = new HashMap<>();
		outputsMap = new HashMap<>();
		for (Interaction scenario : scenarios) {
			read(scenario);
		}
		for (Interaction interaction : states) {
			statesMap.put(interaction.getFixtureName(), new ArrayList<>());
		}
		for (Interaction interaction : states) {
			statesMap.get(interaction.getFixtureName()).add(interaction);
		}
		for (Input input : inputs) {
			inputsMap.put(input.getFixtureName(), new ArrayList<>());
		}
		for (Input input : inputs) {
			inputsMap.get(input.getFixtureName()).add(input);
		}

		for (Output output : outputs) {
			outputsMap.put(output.getFixtureName(), new ArrayList<>());
		}
		for (Output output : outputs) {
			outputsMap.get(output.getFixtureName()).add(output);
		}
	}

	private void read(Interaction interaction) {
		states.add(interaction);
		if (interaction.getTransaction() != null) {
			read(interaction.getTransaction().getTarget());
		}
		for (Input input : interaction.getInputs()) {
			inputs.add(input);
		}
		for (Output output : interaction.getOutputs()) {
			outputs.add(output);
		}
	}

	@Override
	public String toString() {
		String interactions = "";
		for (String key : statesMap.keySet()) {
			interactions += String.format("%s	%s\n", key, statesMap.get(key).size());
		}
		String inputs = "";
		for (String key : inputsMap.keySet()) {
			inputs += String.format("%s	%s\n", key, inputsMap.get(key).size());
		}

		String outputs = "";
		for (String key : outputsMap.keySet()) {
			outputs += String.format("%s	%s\n", key, outputsMap.get(key).size());
		}
		return String.format("Transactions\n%s\n\n\nInputs\n%s\n\n\nOutputs\n%s", interactions, inputs, outputs);
	}

}
