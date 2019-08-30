package test.net.douglashiura.sc3n4r10.glue.code;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.scenario.glue.code.ClassTemplate;
import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class TestGlueCodeOfAnInterection {

	private String _package;
	private String _import;
	private String methods;
	private String body;
	private Interaction interaction;
	private Input input;
	private Output output;
	private Interaction interaction2;

	@Before
	public void setUp() {
		_package = "package com.net.douglashiura.example;\n";
		_import = "import net.douglashiura.us.Fixture;\n";
		methods = "";
		body = "@Fixture(\"EightPuzzle\")\npublic class FixtureEightPuzzle {" + methods + "\n}";
		interaction = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
		interaction2 = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
		input = new Input(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "home1", "");
		output = new Output(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "home1", "");
	}

	@Test
	public void anInteraction() throws Exception {
		String klasse = _package + _import + body;
		ClassTemplate template = new ClassTemplate("com.net.douglashiura.example", Arrays.asList(interaction));
		assertEquals(klasse, template.toString());
	}

	@Test
	public void input() throws Exception {
		String inputMethod = "	public void setHome1(String value){\n	}";
		String klasse = _package + _import + body.replace("}", inputMethod + "\n}");
		interaction.addInput(input);
		ClassTemplate template = new ClassTemplate("com.net.douglashiura.example", Arrays.asList(interaction));
		assertEquals(klasse, template.toString());
	}

	@Test
	public void inputInTwoMethods() throws Exception {
		String inputMethod = "	public void setHome1(String value){\n	}";
		String inputMethod2 = "\n	public void setHome2(String value){\n	}";
		String klasse = _package + _import + body.replace("}", inputMethod + inputMethod2 + "\n}");
		interaction.addInput(input);
		interaction2.addInput(new Input(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "home2", ""));
		ClassTemplate template = new ClassTemplate("com.net.douglashiura.example",
				Arrays.asList(interaction, interaction2));
		assertEquals(klasse, template.toString());
	}

	@Test
	public void transition() throws Exception {
		String inputMethod = "	public void toAnother(String action){\n	}";
		String klasse = _package + _import + body.replace("}", inputMethod + "\n}");
		Interaction aTarget = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "Another");
		interaction.to(aTarget, UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"),"OK");
		ClassTemplate template = new ClassTemplate("com.net.douglashiura.example", Arrays.asList(interaction));
		assertEquals(klasse, template.toString());
	}

	@Test
	public void output() throws Exception {
		String inputMethod = "	public String getHome1(){\n		return null;\n	}";
		String klasse = _package + _import + body.replace("}", inputMethod + "\n}");
		interaction.addOutput(output);
		ClassTemplate template = new ClassTemplate("com.net.douglashiura.example", Arrays.asList(interaction));
		assertEquals(klasse, template.toString());
	}

}
