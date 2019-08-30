package net.douglashiura.scenario.plugin.type;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;
import net.douglashiura.us.serial.Transaction;

public class ExtractPahts {
	private ArrayList<Interaction> paths;

	public ExtractPahts(List<InteractionGeometry> interactionsStarts) {
		paths = new ArrayList<Interaction>();
		for (InteractionGeometry start : interactionsStarts) {
			List<Interaction> currentPath = new ArrayList<Interaction>();
			currentPath.add(extractInteraction(start));
			readAllPahts(currentPath, start);
		}
	}

	private void readAllPahts(List<Interaction> currentPath, InteractionGeometry point) {
		if (point.getTransaction() == null || point.getTransaction().getTargets().isEmpty()) {
			paths.add(currentPath.get(0));
		} else {
			for (InteractionAction interactionGeometry : point.getTransaction().getTargets()) {
				List<Interaction> newPath = clone(currentPath);
				Interaction newInteraction = extractInteraction(interactionGeometry.getTarget());
				Interaction last = newPath.get(newPath.size() - 1);
				last.to(newInteraction, point.getTransaction().getUuid(),interactionGeometry.getAction());
				last.getTransaction().hashCode();
				newPath.add(newInteraction);
				readAllPahts(newPath, interactionGeometry);
			}
		}
	}

	private void readAllPahts(List<Interaction> currentPath, InteractionAction point) {
		if (point.getTarget().getTransaction() == null || point.getTarget().getTransaction().getTargets().isEmpty()) {
			paths.add(currentPath.get(0));
		} else {
			for (InteractionAction interactionGeometry : point.getTarget().getTransaction().getTargets()) {
				List<Interaction> newPath = clone(currentPath);
				Interaction newInteraction = extractInteraction(interactionGeometry.getTarget());
				Interaction last = newPath.get(newPath.size() - 1);
				last.to(newInteraction, point.getTarget().getTransaction().getUuid(),interactionGeometry.getAction());
				last.getTransaction().hashCode();
				newPath.add(newInteraction);
				readAllPahts(newPath, interactionGeometry);
			}
		}
	}

	private List<Interaction> clone(List<Interaction> currentPath) {
		List<Interaction> newCurrentPath = new ArrayList<Interaction>();
		for (Interaction interaction : currentPath) {
			newCurrentPath.add(cloneInteraction(interaction));
		}
		for (int i = 0; i < newCurrentPath.size() - 1; i++) {
			Transaction transaction = newCurrentPath.get(i).getTransaction();
			newCurrentPath.get(i).to(newCurrentPath.get(i + 1), transaction.getUuid(),transaction.getAction());
		}
		return newCurrentPath;
	}

	private Interaction cloneInteraction(Interaction interaction) {
		Interaction aInteraction = new Interaction(interaction.getUuid(), interaction.getFixtureName());
		for (Input input : interaction.getInputs()) {
			aInteraction.addInput(new Input(input.getUuid(), input.getFixtureName(), input.getValue()));
		}
		List<Output> outputs = interaction.getOutputs();
		for (Output output : outputs) {
			aInteraction.addOutput(new Output(output.getUuid(), output.getFixtureName(), output.getValue()));
		}
		Transaction transaction = interaction.getTransaction();
		if (transaction != null && transaction.getTarget() != null) {
			aInteraction.to(transaction.getTarget(), transaction.getUuid(), transaction.getAction());
		}
		return aInteraction;
	}

	private Interaction extractInteraction(InteractionGeometry interactionGeometry) {
		Interaction newInteraction = new Interaction(interactionGeometry.getId(), interactionGeometry.getFixtureName());
		List<InputGeometry> inputs = interactionGeometry.getInputs();
		for (InputGeometry inputGeometry : inputs) {
			newInteraction.addInput(
					new Input(inputGeometry.getId(), inputGeometry.getFixtureName(), inputGeometry.getValue()));
		}
		List<OutputGeometry> outputs = interactionGeometry.getOutputs();
		for (OutputGeometry outputGeometry : outputs) {
			newInteraction.addOutput(
					new Output(outputGeometry.getId(), outputGeometry.getFixtureName(), outputGeometry.getValue()));
		}
		return newInteraction;
	}

	public List<Interaction> pathsOfExecution() {
		return paths;
	}

}
