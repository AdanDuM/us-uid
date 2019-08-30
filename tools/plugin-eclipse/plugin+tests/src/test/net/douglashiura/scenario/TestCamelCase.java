package test.net.douglashiura.scenario;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.us.CamelCase;

public class TestCamelCase {

	@Test
	public void camel() throws Exception {
		assertEquals("Model", CamelCase.transform("model"));
		assertEquals("", CamelCase.transform(null));
		assertEquals("", CamelCase.transform(""));
		assertEquals("M", CamelCase.transform("m"));
	}

	@Test
	public void to() throws Exception {
		assertEquals("toModel", CamelCase.to("model"));
		assertEquals("to", CamelCase.to(null));
		assertEquals("to", CamelCase.to(""));
		assertEquals("toM", CamelCase.to("m"));
	}

	@Test
	public void get() throws Exception {
		assertEquals("getModel", CamelCase.get("model"));
		assertEquals("get", CamelCase.get(null));
		assertEquals("get", CamelCase.get(""));
		assertEquals("getM", CamelCase.get("m"));
	}

	@Test
	public void set() throws Exception {
		assertEquals("setModel", CamelCase.set("model"));
		assertEquals("set", CamelCase.set(null));
		assertEquals("set", CamelCase.set(""));
		assertEquals("setM", CamelCase.set("m"));
	}

}
