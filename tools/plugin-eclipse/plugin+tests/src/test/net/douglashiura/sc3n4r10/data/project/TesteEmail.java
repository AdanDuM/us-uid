package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailBiggerThat120Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailNullException;

public class TesteEmail {
	@Test(expected = EmailInvalidException.class)
	public void invalid() throws Exception {
		new Email(" a invalid");
	}

	@Test(expected = EmailNullException.class)
	public void nullo() throws Exception {
		new Email(null);
	}

	@Test(expected = EmailEmptyException.class)
	public void empty() throws Exception {
		new Email("");
	}

	@Test(expected = EmailBiggerThat120Exception.class)
	public void bigger() throws Exception {
		new Email(
				"a23456789@a23456789.a23456789.a23456789.a23456789.a23456789.a23456789.a23456789.a23456789.a23456789.a23456789.a2345678.ns");
	}

	@Test
	public void email() throws Exception {
		Email douglashiura = new Email("douglashiura@gmail.com");
		assertEquals("douglashiura", douglashiura.getUser());
		assertEquals("gmail.com", douglashiura.getDomain());
		assertEquals("douglashiura@gmail.com", douglashiura.getEmail());
	}

}
