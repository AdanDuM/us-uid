package net.douglashiura.scenario.glue.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class ClassTemplate {

	private String _package;
	private List<Interaction> interactions;

	public ClassTemplate(String _package, List<Interaction> interactions) {
		this._package = _package;
		this.interactions = interactions;
	}

	@Override
	public String toString() {
		String klassTemplate = "package {PACKAGE};" + "\nimport net.douglashiura.us.Fixture;"
				+ "\n@Fixture(\"{FIXTURE_NAME}\")" + "\npublic class Fixture{FIXTURE_NAME} " + "{" + "{METHODS}"
				+ "\n}";

		return klassTemplate.replace("{PACKAGE}", _package)
				.replace("{FIXTURE_NAME}", interactions.get(0).getFixtureName()).replace("{METHODS}", mountMethods());
	}

	private CharSequence mountMethods() {
		return inputsMethods() + outputMethods() + transactionMethods();
	}

	private CharSequence transactionMethods() {
		String template = "	public void to{FIXTURE_NAME}(String action){\n	}";
		String methods = "";
		Set<String> methodsList = new HashSet<>();
		for (Interaction interaction : interactions) {
			if (interaction.getTransaction() != null) {
				methodsList.add(interaction.getTransaction().getTarget().getFixtureName());
			}
		}
		for (String string : methodsList) {
			CharSequence camelCase = string.substring(0, 1).toUpperCase() + string.substring(1);
			methods += "\n" + template.replace("{FIXTURE_NAME}", camelCase);
		}
		return methods;
	}

	private String outputMethods() {
		String template = "	public String get{FIXTURE_NAME}(){\n		return null;\n	}";
		String methods = "";
		Set<String> methodsList = new HashSet<>();
		for (Interaction interaction : interactions) {
			for (Output output : interaction.getOutputs()) {
				methodsList.add(output.getFixtureName());
			}
		}
		for (String string : emOrdem(methodsList)) {
			CharSequence camelCase = string.substring(0, 1).toUpperCase() + string.substring(1);
			methods += "\n" + template.replace("{FIXTURE_NAME}", camelCase);
		}
		return methods;
	}

	private List<String> emOrdem(Set<String> methodsList) {
		List<String> list = new ArrayList<>(methodsList);
		Collections.sort(list);
		return list;
	}

	private CharSequence inputsMethods() {
		String template = "	public void set{FIXTURE_NAME}(String value){\n	}";
		String methods = "";
		Set<String> methodsList = new HashSet<>();
		for (Interaction interaction : interactions) {
			for (Input input : interaction.getInputs()) {
				methodsList.add(input.getFixtureName());
			}
		}
		for (String string : emOrdem(methodsList)) {
			CharSequence camelCase = string.substring(0, 1).toUpperCase() + string.substring(1);
			methods += "\n" + template.replace("{FIXTURE_NAME}", camelCase);
		}
		return methods;
	}

	public String getName() {
		return "Fixture"+interactions.get(0).getFixtureName();
	}

	public String getPackage(String pathseparator) {
		return _package.replace(".", pathseparator);
	}

}