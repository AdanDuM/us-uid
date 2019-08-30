package tests.br.ufsc.leb.adangomes.us.serial;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import net.douglashiura.us.serial.InputFile;
import net.douglashiura.us.serial.Interaction;

public class InputFileTest {

	@Test
	public void createInput() throws Exception {
		String file = "file_name";
		Interaction content = new Interaction(UUID.randomUUID(), "");
		InputFile input = new InputFile(file, content,1);
		assertEquals(file, input.getFile());
		assertEquals(content, input.getScenario());
	}

}
