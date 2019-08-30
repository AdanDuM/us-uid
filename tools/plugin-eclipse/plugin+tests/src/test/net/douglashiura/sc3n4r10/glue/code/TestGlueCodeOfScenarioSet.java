package test.net.douglashiura.sc3n4r10.glue.code;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.scenario.glue.code.GlueCodeOfScenarioSet;
import net.douglashiura.us.serial.Interaction;

public class TestGlueCodeOfScenarioSet {

	private String _package;
	private String _import;
	private String methods;
	private String body;
	private Interaction interaction;
	private Interaction interaction2;

	@Before
	public void setUp() {
		_package = "package com.net.douglashiura.example;\n";
		_import = "import net.douglashiura.us.Fixture;\n";
		methods = "";
		body = "@Fixture(\"EightPuzzle\")\npublic class FixtureEightPuzzle {" + methods + "\n}";
		interaction = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
		interaction2 = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
	}

	@Test
	public void anInteraction() throws Exception {
		String klasse = _package + _import + body;
		GlueCodeOfScenarioSet template = new GlueCodeOfScenarioSet("com.net.douglashiura.example",
				Arrays.asList(interaction, interaction2));
		assertEquals(1, template.getClasses().size());
		assertEquals(klasse, template.getClasses().get(0).toString());
	}

}
