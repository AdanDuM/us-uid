package test.net.douglashiura.scenario.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import net.douglashiura.scenario.plugin.type.ExtractPahts;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.Scenario;
import net.douglashiura.us.serial.Interaction;

public class ScenarioExtractTest {

	@Test
	public void tree() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("Tree.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		InteractionGeometry origin = scenario.starts().get(0);
		assertEquals(1, scenario.starts().size());
		assertNotNull(origin);
		assertEquals("Origin", origin.getFixtureName());
		assertNotNull(origin.getTransaction());
		assertNull(origin.getTransaction().getTargets().get(0).getTarget().getTransaction());
		assertEquals(2, origin.getTransaction().getTargets().size());
		assertEquals("Target2", origin.getTransaction().getTargets().get(0).getTarget().getFixtureName());
		assertEquals("OK", origin.getTransaction().getTargets().get(0).getAction());
		assertEquals("Target1", origin.getTransaction().getTargets().get(1).getTarget().getFixtureName());
		assertEquals("Cancel", origin.getTransaction().getTargets().get(1).getAction());
	}

	@Test
	public void complex() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("Complex.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		List<InteractionGeometry> starts = scenario.starts();
		InteractionGeometry origin = starts.get(0);
		InteractionGeometry origin1 = starts.get(1);
		assertEquals(2, starts.size());
		assertNotNull(origin);
		assertEquals("Origin1", origin1.getFixtureName());
		assertEquals("Origin", origin.getFixtureName());
		assertEquals(1, origin.getTransaction().getTargets().size());
		assertEquals("Target1", origin.getTransaction().getTargets().get(0).getTarget().getFixtureName());
		assertEquals("Target3", origin.getTransaction().getTargets().get(0).getTarget().getTransaction().getTargets()
				.get(0).getTarget().getFixtureName());
		assertEquals("Target4", origin.getTransaction().getTargets().get(0).getTarget().getTransaction().getTargets()
				.get(1).getTarget().getFixtureName());
		assertEquals(2, origin1.getTransaction().getTargets().size());
		assertEquals("Target1", origin1.getTransaction().getTargets().get(0).getTarget().getFixtureName());
		assertEquals("Target2", origin1.getTransaction().getTargets().get(1).getTarget().getFixtureName());
		assertEquals("Target3", origin1.getTransaction().getTargets().get(0).getTarget().getTransaction().getTargets()
				.get(0).getTarget().getFixtureName());
		assertEquals("Target4", origin1.getTransaction().getTargets().get(0).getTarget().getTransaction().getTargets()
				.get(1).getTarget().getFixtureName());
	}

	@Test
	public void threeInteractions() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("threeInteractions.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		List<Interaction> paths = new ExtractPahts(scenario.starts()).pathsOfExecution();
		Interaction origin = paths.get(0);
		assertEquals(1, paths.size());
		assertEquals("Origin", origin.getFixtureName());
		assertEquals("Target1", origin.getTransaction().getTarget().getFixtureName());
		assertEquals("Target2", origin.getTransaction().getTarget().getTransaction().getTarget().getFixtureName());
	}

	@Test
	public void complexPaths() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("Complex.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		List<Interaction> starts = new ExtractPahts(scenario.starts()).pathsOfExecution();
		Interaction origin = starts.get(0);
		Interaction origin1 = starts.get(1);
		Interaction origin2 = starts.get(2);
		Interaction origin3 = starts.get(3);
		Interaction origin4 = starts.get(4);
		assertEquals(5, starts.size());
		assertEquals("Origin", origin.getFixtureName());
		assertEquals("Target1", origin.getTransaction().getTarget().getFixtureName());
		assertEquals("Target3", origin.getTransaction().getTarget().getTransaction().getTarget().getFixtureName());
		assertEquals("Origin", origin1.getFixtureName());
		assertEquals("Target1", origin1.getTransaction().getTarget().getFixtureName());
		assertEquals("Target4", origin1.getTransaction().getTarget().getTransaction().getTarget().getFixtureName());
		assertEquals("Origin1", origin2.getFixtureName());
		assertEquals("Target1", origin2.getTransaction().getTarget().getFixtureName());
		assertEquals("Target3", origin2.getTransaction().getTarget().getTransaction().getTarget().getFixtureName());
		assertEquals("Origin1", origin3.getFixtureName());
		assertEquals("Target1", origin3.getTransaction().getTarget().getFixtureName());
		assertEquals("Target4", origin3.getTransaction().getTarget().getTransaction().getTarget().getFixtureName());
		assertEquals("Origin1", origin4.getFixtureName());
		assertEquals("Target1", origin2.getTransaction().getTarget().getFixtureName());
		assertNotSame(origin1.getTransaction().getTarget(), origin.getTransaction().getTarget());
		assertSame(origin1.getTransaction().getTarget().getFixtureName(),
				origin.getTransaction().getTarget().getFixtureName());
	}

	@Test
	public void treePaths() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("Tree.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		List<Interaction> pathsOfExecution = new ExtractPahts(scenario.starts()).pathsOfExecution();
		assertEquals(2, pathsOfExecution.size());
		assertEquals("Origin", pathsOfExecution.get(0).getFixtureName());
		assertEquals("Origin", pathsOfExecution.get(1).getFixtureName());
		assertNull(pathsOfExecution.get(0).getTransaction().getTarget().getTransaction());
		assertNull(pathsOfExecution.get(1).getTransaction().getTarget().getTransaction());
		assertEquals("Target2", pathsOfExecution.get(0).getTransaction().getTarget().getFixtureName());
		assertEquals("Target1", pathsOfExecution.get(1).getTransaction().getTarget().getFixtureName());
		assertEquals(2, pathsOfExecution.size());
	}

	@Test
	public void twoOrigin() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("TwoOrigin.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		List<InteractionGeometry> origins = scenario.starts();
		InteractionGeometry origin = origins.get(0);
		InteractionGeometry origin1 = origins.get(1);
		assertEquals(2, scenario.starts().size());
		assertEquals("Origin2", origin.getFixtureName());
		assertNotNull(origin.getTransaction());
		assertNull(origin.getTransaction().getTargets().get(0).getTarget().getTransaction());
		assertEquals(1, origin.getTransaction().getTargets().size());
		assertEquals("Target", origin1.getTransaction().getTargets().get(0).getTarget().getFixtureName());
		assertEquals("Source1", origin1.getFixtureName());
		assertNotNull(origin1.getTransaction());
		assertNull(origin1.getTransaction().getTargets().get(0).getTarget().getTransaction());
		assertEquals(1, origin1.getTransaction().getTargets().size());
		assertEquals("Target", origin1.getTransaction().getTargets().get(0).getTarget().getFixtureName());

	}

	@Test
	public void anInteraction() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("AnInteraction.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		InteractionGeometry origin = scenario.starts().get(0);
		assertEquals(1, scenario.starts().size());
		assertNotNull(origin);
		assertEquals("Origin", origin.getFixtureName());
		assertNull(origin.getTransaction());
	}

	@Test
	public void twoInteractions() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("TwoInteractions.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		InteractionGeometry origin = scenario.starts().get(0);
		assertNotNull(origin);
		assertEquals("Origin", origin.getFixtureName());
		assertNotNull(origin.getTransaction());
		assertEquals(1, origin.getTransaction().getTargets().size());
		assertEquals("Target", origin.getTransaction().getTargets().get(0).getTarget().getFixtureName());
	}

	@Test
	public void twoInputs() throws Exception {
		InputStream source = this.getClass().getResourceAsStream("TwoInputs.us.temp");
		byte[] bytes = new byte[source.available()];
		source.read(bytes);
		String jsonText = new String(bytes);
		source.close();
		Scenario scenario = new Scenario(jsonText);
		InteractionGeometry origin = scenario.starts().get(0);
		assertEquals(1, scenario.starts().size());
		assertNotNull(origin);
		assertEquals("Origin", origin.getFixtureName());
		assertNull(origin.getTransaction());
		assertEquals(2, origin.getInputs().size());
	}

}
