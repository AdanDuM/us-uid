package test.net.douglashiura.sc3n4r10.glue.code;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.scenario.glue.code.ExtractInteractions;
import net.douglashiura.us.serial.Interaction;

public class TestExtractInteractionsOfScenarios {
	private Interaction scenario;
	private Interaction scenario2;

	@Before
	public void setUp() {
		scenario = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
		scenario2 = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "EightPuzzle");
		scenario2.to(scenario, UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"),"Ok");
	}

	@Test
	public void anInteraction() throws Exception {
		List<Interaction> interactions = ExtractInteractions.from(Arrays.asList(scenario));
		assertEquals(1, interactions.size());
		assertEquals(scenario, interactions.get(0));
	}

	@Test
	public void threeInteraction() throws Exception {
		List<Interaction> interactions = ExtractInteractions.from(Arrays.asList(scenario));
		assertEquals(1, interactions.size());
		assertEquals(scenario, interactions.get(0));
	}

}
