package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Transaction;

public class TransactionTest {

	@Test
	public void createTransaction() throws Exception {
		UUID uuid = UUID.randomUUID();
		Interaction travelsGuide = new Interaction(UUID.randomUUID(), "TravelsGuide");
		Interaction destination = new Interaction(UUID.randomUUID(), "Destination");
		Transaction transaction = new Transaction(uuid, travelsGuide, destination,"OK");
		assertEquals(uuid, transaction.getUuid());
		assertEquals(destination, transaction.getTarget());
	}

}
