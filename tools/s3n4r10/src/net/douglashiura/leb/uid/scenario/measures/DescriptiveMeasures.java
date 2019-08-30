package net.douglashiura.leb.uid.scenario.measures;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;
import net.douglashiura.us.serial.Transaction;

public class DescriptiveMeasures {

	private List<Interaction> scenarios;
	private List<Interaction> states;
	private List<Input> inputs;
	private List<Output> outputs;
	private List<Transaction> transactions;

	public DescriptiveMeasures(List<Interaction> scenarios) {
		this.scenarios = scenarios;
		states = new ArrayList<>();
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		transactions = new ArrayList<>();
		for (Interaction scenario : scenarios) {
			read(scenario);
		}
	}

	private void read(Interaction interaction) {
		states.add(interaction);
		if (interaction.getTransaction() != null) {
			transactions.add(interaction.getTransaction());
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
		return String.format("Scenarios	States	Outputs	Inputs	Transactions\n%s	%s	%s	%s	%s", scenarios.size(),
				states.size(), outputs.size(), inputs.size(), transactions.size());
	}

}
