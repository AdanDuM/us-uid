package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;

public class InteractionOutputTest {

	private Interaction travelsGuide;
	private Output title;

	@Before
	public void setUp() {
		travelsGuide = new Interaction(UUID.randomUUID(), "TravelsGuide");
		title = new Output(UUID.randomUUID(), "title", "Travel's Guide");
	}
	
	@Test
	public void addOutput() throws Exception {
		travelsGuide.addOutput(title);
		assertEquals(1, travelsGuide.getOutputs().size());
		assertEquals(title, travelsGuide.getOutputs().get(0));
	}
	
	@Test
	public void addTwoOutputs() throws Exception {
		Output destination = new Output(UUID.randomUUID(), "destination", "Destination");
		travelsGuide.addOutput(title);
		travelsGuide.addOutput(destination);
		assertEquals(2, travelsGuide.getOutputs().size());
		assertEquals(title, travelsGuide.getOutputs().get(0));
		assertEquals(destination, travelsGuide.getOutputs().get(1));
	}
	
}
