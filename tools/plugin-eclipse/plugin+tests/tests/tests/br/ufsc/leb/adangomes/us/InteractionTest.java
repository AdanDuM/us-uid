package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.Interaction;

public class InteractionTest {

	@Test
	public void is() throws Exception {
		UUID uuid = UUID.randomUUID();
		Interaction travelsGuide = new Interaction(uuid, "TravelsGuide");
		assertEquals(uuid, travelsGuide.getUuid());
		assertEquals("TravelsGuide", travelsGuide.getFixtureName());
		assertEquals(null, travelsGuide.getTransaction());
		assertEquals(0, travelsGuide.getInputs().size());
		assertEquals(0, travelsGuide.getOutputs().size());
	}

}