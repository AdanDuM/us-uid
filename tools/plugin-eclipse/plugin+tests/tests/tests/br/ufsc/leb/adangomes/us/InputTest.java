package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.Input;

public class InputTest {

	@Test
	public void createInput() throws Exception {
		UUID uuid = UUID.randomUUID();
		Input country = new Input(uuid, "country", "Brazil") ;
		assertEquals(uuid, country.getUuid());
		assertEquals("country", country.getFixtureName());
		assertEquals("Brazil", country.getValue());
	}
	
}
