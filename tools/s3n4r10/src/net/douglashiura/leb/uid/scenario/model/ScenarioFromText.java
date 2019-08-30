package net.douglashiura.leb.uid.scenario.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Output;

public class ScenarioFromText {

	private static final String INTERACTION = "br.ufsc.leb.uid.scenario.Interacao";
	private static final String OUTPUT = "br.ufsc.leb.uid.scenario.SystemOutput";
	private static final String INPUT = "br.ufsc.leb.uid.scenario.UserInput";
	private static final String TRANSACTION = "br.ufsc.leb.uid.scenario.Transaction";
	private HashMap<UUID, InteractionTree> interactions;
	private List<InteractionTree> firsts;
	private List<LinkedTreeMap<String, ?>> elements;
	private String file;

	@SuppressWarnings("unchecked")
	public ScenarioFromText(String jsonText,String file) {
		this.file=file;	
		if (jsonText == null || jsonText.equals(""))
			jsonText = "[]";
		this.elements = new GsonBuilder().create().fromJson(jsonText, List.class);
		this.interactions = new HashMap<UUID, InteractionTree>();
		extractInteractons();
		extractInputs();
		extractOutputs();
		extractTransaction();
		firsts = extractFirstState();
	}

	public List<InteractionTree> starts() {
		return firsts;
	}

	public List<InteractionTree> getInteractionsUnsctrutured() {
		return new ArrayList<>(interactions.values());
	}

	private List<InteractionTree> extractFirstState() {
		List<InteractionTree> firsts = new ArrayList<InteractionTree>();
		for (InteractionTree aState : interactions.values()) {
			if (nonArrival(aState)) {
				firsts.add(aState);
			}
		}
		return firsts;
	}

	private boolean nonArrival(InteractionTree nonTarget) {
		for (InteractionTree aState : interactions.values()) {
			TransactionTree transaction = aState.getTransaction();
			if (transaction != null && contains(nonTarget, transaction)) {
				return false;
			}
		}
		return true;
	}

	private Boolean contains(InteractionTree nonTarget, TransactionTree transaction) {
		for (InteractionTree aTransaction : transaction.getTargets()) {
			if (nonTarget.equals(aTransaction)) {
				return true;
			}
		}
		return false;

	}

	private void extractOutputs() {
		for (LinkedTreeMap<String, ?> object : elements) {
			if (OUTPUT.equals(object.get("type"))) {
				InteractionTree composite = null;
				try {
					String uidParent = object.get("composite").toString();
					Output output = new Output(extractId(object), extractFixture(object), extractValue(object));
					composite = interactions.get(UUID.fromString(uidParent));
					composite.addOutput(output);
				} catch (Exception e) {
					System.out.println(extractId(object)+ " "+ extractValue(object)+  " sem composite " + file);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void extractTransaction() {
		HashMap<InteractionTree, TransactionTree> transactions = new HashMap<>();
		for (LinkedTreeMap<String, ?> object : elements) {
			if (TRANSACTION.equals(object.get("type"))) {
				Object source = ((LinkedTreeMap<String, ?>) object.get("source")).get("node");
				Object target = ((LinkedTreeMap<String, ?>) object.get("target")).get("node");
				InteractionTree aSource = interactions.get(UUID.fromString(source.toString()));
				InteractionTree aTarget = interactions.get(UUID.fromString(target.toString()));
				TransactionTree transaction = null;
				if (transactions.containsKey(aSource)) {
					transaction = transactions.get(aSource);
				} else {
					transaction = new TransactionTree(extractId(object), aSource);
					transactions.put(aSource, transaction);
					aSource.setTransaction(transaction);
				}
				transaction.addTarget(aTarget);
			}
		}

	}

	private void extractInputs() {
		for (LinkedTreeMap<String, ?> object : elements) {
			if (INPUT.equals(object.get("type"))) {
				InteractionTree composite = interactions.get(UUID.fromString(object.get("composite").toString()));
				Input input = new Input(extractId(object), extractFixture(object), extractValue(object));
				composite.addInput(input);
			}
		}
	}

	private String extractValue(LinkedTreeMap<String, ?> object) {
		return object.get("text").toString();
	}

	private void extractInteractons() {
		for (LinkedTreeMap<String, ?> object : elements) {
			if (INTERACTION.equals(object.get("type"))) {
				interactions.put(UUID.fromString(object.get("id").toString()), build(object));
			}
		}
	}

	private static InteractionTree build(LinkedTreeMap<String, ?> object) {
		return new InteractionTree(extractId(object), extractFixture(object));
	}

	private static UUID extractId(LinkedTreeMap<String, ?> object) {
		return UUID.fromString(object.get("id").toString());
	}

	private static String extractFixture(LinkedTreeMap<String, ?> object) {
		@SuppressWarnings("unchecked")
		LinkedTreeMap<String, ?> userData = (LinkedTreeMap<String, ?>) object.get("userData");
		return userData.get("model").toString();
	}

}
