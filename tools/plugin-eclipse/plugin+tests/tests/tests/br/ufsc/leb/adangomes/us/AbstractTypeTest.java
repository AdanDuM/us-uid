package tests.br.ufsc.leb.adangomes.us;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.AbstractType;

public class AbstractTypeTest {

	@Test
	public void createAbstractType() throws Exception {
		UUID uuid = UUID.randomUUID();
		AbstractType abstractType = new AbstractType(uuid, "fixtureName", "value") {
			private static final long serialVersionUID = 1L;
		};
		assertEquals(uuid, abstractType.getUuid());
		assertEquals("fixtureName", abstractType.getFixtureName());
		assertEquals("value", abstractType.getValue());
	}

}
