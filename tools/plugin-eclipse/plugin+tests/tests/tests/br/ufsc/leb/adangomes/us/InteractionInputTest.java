package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;

public class InteractionInputTest {

	private Interaction travelsGuide;
	private Input country;

	@Before
	public void setUp() {
		travelsGuide = new Interaction(UUID.randomUUID(), "TravelsGuide");
		country = new Input(UUID.randomUUID(), "country", "Brazil");
	}

	@Test
	public void addInput() throws Exception {
		travelsGuide.addInput(country);
		assertEquals(1, travelsGuide.getInputs().size());
		assertEquals(country, travelsGuide.getInputs().get(0));
	}
	
	@Test
	public void addTwoInputs() throws Exception {
		Input buttonTravel = new Input(UUID.randomUUID(), "buttonTravel", "Travel");
		travelsGuide.addInput(country);
		travelsGuide.addInput(buttonTravel);
		assertEquals(2, travelsGuide.getInputs().size());
		assertEquals(country, travelsGuide.getInputs().get(0));
		assertEquals(buttonTravel, travelsGuide.getInputs().get(1));
	}
	
}
