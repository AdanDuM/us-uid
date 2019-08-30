package net.douglashiura.leb.uid.scenario.measures.uniform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.douglashiura.leb.uid.scenario.EmptyScenarioException;
import net.douglashiura.leb.uid.scenario.data.Scenario;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

public class Pairs {
	public static List<Pair> pairs(List<Scenario> scenarios) throws IOException, NotAFileException {
		List<Pair> pairs = new ArrayList<>();
		for (int i = 0; i < scenarios.size(); i++) {
			for (int j = 0; j < scenarios.size(); j++) {
				if (i != j) {
					try {
						pairs.add(new Pair(scenarios.get(i), scenarios.get(j)));
					} catch (EmptyScenarioException e) {

					}
				}
			}
		}
		return pairs;
	}
}
