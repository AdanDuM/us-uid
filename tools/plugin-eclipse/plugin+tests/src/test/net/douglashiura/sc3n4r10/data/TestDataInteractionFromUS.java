package test.net.douglashiura.sc3n4r10.data;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.ml.data.DataInteraction;
import net.douglashiura.leb.uid.scenario.ml.data.DataInteractionFromUS;
import net.douglashiura.us.serial.Interaction;

public class TestDataInteractionFromUS {

	@Test
	public void read() throws Exception {
		Interaction first = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "fixture");
		List<DataInteraction> instances = DataInteractionFromUS.read(first, "file");
		assertEquals(1, instances.size());
		assertEquals(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), instances.get(0).getId());
		assertEquals(Integer.valueOf(0), instances.get(0).getEndDistance());
		assertEquals(Integer.valueOf(0), instances.get(0).getInputs());
		assertEquals(Integer.valueOf(0), instances.get(0).getOutputs());
		assertEquals(Integer.valueOf(0), instances.get(0).getElements());
		assertEquals(Integer.valueOf(0), instances.get(0).getStartDistance());
		assertEquals(Integer.valueOf(0), instances.get(0).getDeep());
		assertEquals("file", instances.get(0).getScenario());
		assertEquals("fixture", instances.get(0).getFixture());
	}

}
