package net.douglashiura.scenario.project.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import net.douglashiura.scenario.plugin.type.InputGeometry;
import net.douglashiura.scenario.plugin.type.OutputGeometry;
import net.douglashiura.scenario.project.FileInteraction;

public class AutoNameByUniformity {

	private List<FileInteraction> nameds;
	private ArrayList<FileInteraction> unameds;
	private static final Float CUT_UNIFORMITY = 0.6f;

	public AutoNameByUniformity(List<FileInteraction> elements) {
		nameds = new ArrayList<FileInteraction>();
		unameds = new ArrayList<FileInteraction>();
		for (FileInteraction fileInteraction : elements) {
			if (fileInteraction.getFixtureName() != null && !fileInteraction.getFixtureName().isEmpty()) {
				nameds.add(fileInteraction);
			} else {
				unameds.add(fileInteraction);
			}
		}
	}

	public void naming() throws CoreException {
		Uniformity max = null;
		boolean isNewNamed = true;
		while (isNewNamed) {
			FileInteraction remover = null;
			isNewNamed = false;
			for (FileInteraction unamed : unameds) {
				max = null;
				for (FileInteraction named : nameds) {
					Uniformity actual = new Uniformity(unamed, named);
					if (max == null) {
						max = actual;
					} else if (max.uniformity < actual.uniformity) {
						max = actual;
					}
				}
				isNewNamed = false;
				if (max != null && max.uniformity > CUT_UNIFORMITY) {
					unamed.setFixtureName(max.named.getFixtureName());
					nameds.add(unamed);
					remover = unamed;
					isNewNamed = true;
				}
			}
			unameds.remove(remover);
		}

	}

	class Uniformity {
		public FileInteraction named;
		private Float uniformity;
		private Integer uniformityInputs;
		private Integer uniformityOutputs;
		private Integer nonUniformityInputs;
		private Integer nonUniformityOutputs;
		private List<String> inputs;
		private List<String> outputs;

		private FileInteraction unamed;

		public Uniformity(FileInteraction unamed, FileInteraction named) {
			this.unamed = unamed;
			this.named = named;
			uniformityInputs = 0;
			uniformityOutputs = 0;
			nonUniformityInputs = 0;
			nonUniformityOutputs = 0;
			inputs = new ArrayList<String>();
			outputs = new ArrayList<String>();
			extractStrings(named);
			uniformity = uniformityCalc();
		}

		private void extractStrings(FileInteraction named) {
			for (InputGeometry input : named.getInputs()) {
				inputs.add(input.getValue());
			}
			for (OutputGeometry output : named.getOutputs()) {
				outputs.add(output.getValue());
			}
		}

		private Float uniformityCalc() {
			calcInputs();
			calcOutputs();
			float uniform = uniformityInputs + uniformityOutputs;
			float allElements = uniform + nonUniformityInputs + nonUniformityOutputs;
			try {
				return uniform / allElements;
			} catch (ArithmeticException divideByZero) {
				return 0f;
			}
		}

		private void calcOutputs() {
			for (OutputGeometry output : unamed.getOutputs()) {
				if (outputs.contains(output.getValue())) {
					uniformityOutputs++;
				} else {
					nonUniformityOutputs++;
				}
			}
		}

		private void calcInputs() {
			for (InputGeometry input : unamed.getInputs()) {
				if (inputs.contains(input.getValue())) {
					uniformityInputs++;
				} else {
					nonUniformityInputs++;
				}
			}
		}

		public String getFixtureName() {
			return named.getFixtureName();
		}
	}
}
