package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.Output;

public class OutputTest {

	@Test
	public void createOutput() throws Exception {
		UUID uuid = UUID.randomUUID();
		Output country = new Output(uuid, "title", "Travel's Guide");
		assertEquals(uuid, country.getUuid());
		assertEquals("title", country.getFixtureName());
		assertEquals("Travel's Guide", country.getValue());
	}

}
