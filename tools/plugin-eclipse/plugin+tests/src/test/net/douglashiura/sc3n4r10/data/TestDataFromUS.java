package test.net.douglashiura.sc3n4r10.data;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.ml.data.Data;
import net.douglashiura.leb.uid.scenario.ml.data.DataFromUS;
import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class TestDataFromUS {

	@Test
	public void readInput() throws Exception {
		Interaction first = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "fixture");
		Input input = new Input(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "fixture1", "value");
		first.addInput(input);
		List<Data> instances = DataFromUS.read(first, "file");
		assertEquals(1, instances.size());
		assertEquals(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), instances.get(0).getUuid());
		assertEquals(Integer.valueOf("0"), instances.get(0).getEndDistance());
		assertEquals(Integer.valueOf("1"), instances.get(0).getInputs());
		assertEquals(Integer.valueOf("0"), instances.get(0).getOutputs());
		assertEquals(Integer.valueOf("1"), instances.get(0).getElements());
		assertEquals(Integer.valueOf("0"), instances.get(0).getStartDistance());
		assertEquals(Integer.valueOf("0"), instances.get(0).getDeep());
		assertEquals("file", instances.get(0).getScenario());
		assertEquals("fixture1", instances.get(0).getFixture());
		assertEquals("value", instances.get(0).getValue());
		assertEquals(Data.Type.INPUT, instances.get(0).getType());
	}

	@Test
	public void readOutput() throws Exception {
		Interaction first = new Interaction(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "fixture");
		Output output = new Output(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), "fixture1", "value");
		first.addOutput(output);
		List<Data> instances = DataFromUS.read(first, "file");
		assertEquals(1, instances.size());
		assertEquals(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), instances.get(0).getUuid());
		assertEquals(Integer.valueOf("0"), instances.get(0).getEndDistance());
		assertEquals(Integer.valueOf("0"), instances.get(0).getInputs());
		assertEquals(Integer.valueOf("1"), instances.get(0).getOutputs());
		assertEquals(Integer.valueOf("1"), instances.get(0).getElements());
		assertEquals(Integer.valueOf("0"), instances.get(0).getStartDistance());
		assertEquals(Integer.valueOf("0"), instances.get(0).getDeep());
		assertEquals("file", instances.get(0).getScenario());
		assertEquals("fixture1", instances.get(0).getFixture());
		assertEquals("value", instances.get(0).getValue());
		assertEquals(Data.Type.OUTPUT, instances.get(0).getType());

	}

}
