package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;

public class SimpleNameTest {
	@Test(expected = SimpleNameInvalidException.class)
	public void invalid() throws Exception {
		new SimpleName(" a invalid");
	}

	@Test(expected = SimpleNameInvalidException.class)
	public void invalidWithPlus() throws Exception {
		new SimpleName("douglas+hiura");
	}

	@Test
	public void upper() throws Exception {
		assertEquals("DouglasHiura", new SimpleName("DouglasHiura").getName());
		assertEquals("douglasHiura", new SimpleName("douglasHiura").getName());
		assertEquals("douglasHiura", new SimpleName("douglasHiura").toString());
		assertEquals("douglas-hiura", new SimpleName("douglas-hiura").toString());
		assertEquals("douglashiura", new SimpleName("douglashiura").toString());
		assertEquals(new SimpleName("doug"), new SimpleName("doug"));
	}

	@Test(expected = SimpleNameInvalidException.class)
	public void invalidWithSpecialCharaters() throws Exception {
		new SimpleName("douglashiurá");
	}

	@Test(expected = SimpleNameInvalidException.class)
	public void invalidWithSpecialDot() throws Exception {
		new SimpleName("douglas.hiura");
	}

	@Test(expected = SimpleNameInvalidException.class)
	public void invalidWithSpecialCharaters2() throws Exception {
		new SimpleName("douglaç");
	}

	@Test(expected = UserNameNullException.class)
	public void nullo() throws Exception {
		new SimpleName(null);
	}

	@Test(expected = SimpleNameEmptyException.class)
	public void empty() throws Exception {
		new SimpleName("");
	}

	@Test(expected = SimpleNameBiggerThat30Exception.class)
	public void bigger() throws Exception {
		new SimpleName("a23456789a23456789a23456789abcd");
	}

	@Test
	public void email() throws Exception {
		SimpleName douglashiura = new SimpleName("douglashiura");
		assertEquals("douglashiura", douglashiura.getName());
	}

}
