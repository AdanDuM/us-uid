package net.douglashiura.leb.uid.scenario.measures.uniform.similarity;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class Similarity {
	private List<String> valuesA;
	private List<String> valuesB;
	private float uniformity;

	public Similarity(Interaction uidA, Interaction uidB) {
		valuesA = new ArrayList<String>();
		valuesB = new ArrayList<String>();
		serializeAll(uidA, valuesA);
		serializeAll(uidB, valuesB);
		calculate();
	}

	public static class SimilarityCosine extends Similarity {
		public SimilarityCosine(Interaction uidA, Interaction uidB) {
			super(uidA, uidB);
		}

		float calculateMaxSimilarity(String value, List<String> values) {
			float similarity = 0f;
			StringMetric metric = StringMetrics.cosineSimilarity();
			for (String valueB : values) {
				float similarityLocal = metric.compare(value, valueB);
				if (similarity < similarityLocal)
					similarity = similarityLocal;
			}
			return similarity;
		}
	}

	public static class SimilarityJaccard extends Similarity {
		public SimilarityJaccard(Interaction uidA, Interaction uidB) {
			super(uidA, uidB);
		}

		float calculateMaxSimilarity(String value, List<String> values) {
			float similarity = 0f;
			StringMetric metric = StringMetrics.jaccard();
			for (String valueB : values) {
				float similarityLocal = metric.compare(value, valueB);
				if (similarity < similarityLocal)
					similarity = similarityLocal;
			}
			return similarity;
		}
	}

	public static class SimilarityMongeElkan extends Similarity {
		public SimilarityMongeElkan(Interaction uidA, Interaction uidB) {
			super(uidA, uidB);
		}

		float calculateMaxSimilarity(String value, List<String> values) {
			float similarity = 0f;
			StringMetric metric = StringMetrics.mongeElkan();
			for (String valueB : values) {
				float similarityLocal = metric.compare(value, valueB);
				if (similarity < similarityLocal)
					similarity = similarityLocal;
			}
			return similarity;
		}
	}

	private void calculate() {
		Float similarity = calculateSimiliarity();
		uniformity = (float) similarity / valuesA.size();
	}

	private Float calculateSimiliarity() {
		float similarity = 0f;
		for (String string : valuesA) {
			similarity += calculateMaxSimilarity(string, valuesB);
		}
		return similarity;

	}

	float calculateMaxSimilarity(String string, List<String> valuesB2) {
		return valuesB2.contains(string) ? 1 : 0;
	}

	private void serializeAll(Interaction uidA, List<String> values) {
		if (uidA.getTransaction() != null) {
			serializeAll(uidA.getTransaction().getTarget(), values);
		}
		for (Output output : uidA.getOutputs()) {
			values.add(output.getValue().trim());
		}
		for (Input input : uidA.getInputs()) {
			values.add(input.getValue().trim());
		}
	}

	public Float getSimilarity() {
		return uniformity;
	}
}
