package net.douglashiura.leb.uid.scenario.glue.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.douglashiura.us.Interaction;

public class GlueCodeOfScenarioSet {

	private Map<String, List<Interaction>> map;
	private String _package;

	public GlueCodeOfScenarioSet(String _package, List<Interaction> interactions) {
		this._package = _package;
		map = new HashMap<>();
		for (Interaction interaction : interactions) {
			map.put(interaction.getFixture(), new ArrayList<>());
		}
		for (Interaction interaction : interactions) {
			map.get(interaction.getFixture()).add(interaction);
		}
	}
	public List<ClassTemplate> getClasses() {
		List<ClassTemplate> list = new ArrayList<>();
		for (List<Interaction> interactions : map.values()) {
			list.add(new ClassTemplate(_package, interactions));
		}
		return list;
	}

}
