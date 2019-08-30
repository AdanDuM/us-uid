package net.douglashiura.scenario.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;

import net.douglashiura.scenario.plugin.type.InputGeometry;
import net.douglashiura.scenario.plugin.type.InteractionAction;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.OutputGeometry;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.plugin.type.TransactionGeometry;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.scenario.project.util.Files;

public class Elements {

	public static Map<UUID, Rateable> from(List<InteractionGeometry> firstStates) {
		HashMap<UUID, Rateable> map = new HashMap<UUID, Rateable>();
		for (InteractionGeometry firstState : firstStates) {
			ofInteraction(firstState, map);
		}
		return map;
	}

	private static void ofInteraction(InteractionGeometry firstState, HashMap<UUID, Rateable> map) {
		map.put(firstState.getId(), firstState);
		for (InputGeometry aInput : firstState.getInputs())
			map.put(aInput.getId(), aInput);
		for (OutputGeometry aOutput : firstState.getOutputs())
			map.put(aOutput.getId(), aOutput);
		if (firstState.getTransaction() != null) {
			TransactionGeometry transaction = firstState.getTransaction();
			map.put(transaction.getUuid(), transaction);
			List<InteractionAction> transactions = transaction.getTargets();
			for (InteractionAction interactionGeometry : transactions) {
				ofInteraction(interactionGeometry.getTarget(), map);
			}
		}

	}

	public static List<Element> ofInputsAndOutputsFrom(List<IJavaProject> projects) throws CoreException, IOException {
		List<Element> list = new ArrayList<>();
		for (IJavaProject iJavaProject : projects) {
			List<FileScenario> files = Files.from(iJavaProject.getProject());
			for (FileScenario fileScenario : files) {
				for (InteractionGeometry aInteraction : fileScenario.getAllInteractions()) {
					for (InputGeometry aInput : aInteraction.getInputs()) {
						list.add(new Element(fileScenario, aInteraction, aInput));
					}
					for (OutputGeometry aOutput : aInteraction.getOutputs()) {
						list.add(new Element(fileScenario, aInteraction, aOutput));
					}
				}
			}

		}
		return list;
	}

	public static List<FileInteraction> ofInteractionsFrom(List<IJavaProject> projects) throws CoreException {
		List<FileInteraction> list = new ArrayList<>();
		for (IJavaProject iJavaProject : projects) {
			List<FileScenario> files = Files.from(iJavaProject.getProject());
			for (FileScenario fileScenario : files) {
				for (InteractionGeometry aInteraction : fileScenario.getAllInteractions()) {
					list.add(new FileInteraction(fileScenario, aInteraction));
				}
			}

		}
		return list;
	}
}
