package net.douglashiura.leb.uid.scenario.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Output;

public class InteractionTree {

	private TransactionTree transaction;
	private List<Input> inputs;
	private List<Output> outputs;

	public InteractionTree(UUID id, String fixtureName) {
		inputs = new ArrayList<Input>();
		outputs = new ArrayList<Output>();
	}

	public void addInput(Input input) {
		inputs.add(input);
	}

	public TransactionTree getTransaction() {
		return transaction;
	}

	public void addOutput(Output output) {
		outputs.add(output);
	}

	public void setTransaction(TransactionTree transaction) {
		this.transaction = transaction;

	}

	public Collection<? extends Input> getInputs() {
		return inputs;
	}

	public Collection<? extends Output> getOutputs() {
		return outputs;
	}

}
