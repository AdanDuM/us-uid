package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.Interaction;

public class InteractionTransactionTest {

	@Test
	public void travelsGuideToDestination() throws Exception {
		UUID uuid = UUID.randomUUID();
		Interaction travelsGuide = new Interaction(UUID.randomUUID(), "TravelsGuide");
		Interaction destination = new Interaction(UUID.randomUUID(), "Destination");
		travelsGuide.to(destination, uuid,"OK");
		assertEquals(destination, travelsGuide.getTransaction().getTarget());
		assertEquals(uuid, travelsGuide.getTransaction().getUuid());
	}

}
