package tests.br.ufsc.leb.adangomes.us.serial;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

import net.douglashiura.us.serial.Results;

public class ResultsTest {

	@Test
	public void verifyResultsColor_Ok_ExpectGreen() throws Exception {
		Results result = Results.OK;
		assertEquals(Color.GREEN, result.getColor());
	}

	@Test
	public void verifyResultsColor_Error_ExpectRed() throws Exception {
		Results result = Results.ERROR;
		assertEquals(Color.RED, result.getColor());
	}

	@Test
	public void verifyResultsColor_Fail_ExpectBlue() throws Exception {
		Results result = Results.FAIL;
		assertEquals(Color.BLUE, result.getColor());
	}

	@Test
	public void verifyResultsColor_NotExecuted_ExpectBlack() throws Exception {
		Results result = Results.NOT_EXECUTED;
		assertEquals(Color.BLACK, result.getColor());
	}

	@Test
	public void verifyResultsColor_End_ExpectBlack() throws Exception {
		Results result = Results.END;
		assertEquals(Color.BLACK, result.getColor());
	}

}
