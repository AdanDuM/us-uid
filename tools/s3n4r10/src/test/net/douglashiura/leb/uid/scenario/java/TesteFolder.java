package test.net.douglashiura.leb.uid.scenario.java;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.Folder;
import net.douglashiura.leb.uid.scenario.data.Scenario;

public class TesteFolder {

	@Test
	public void javaStyle() throws Exception {
		Folder folder = Folder.getDefault();
		List<Scenario> scenaries = folder.getScenaries();
		List<Folder> folders = folder.getFolders();
		assertEquals(0, scenaries.size());
		assertEquals(1, folders.size());
		assertEquals(new Integer(0), folder.getDeep());
		assertEquals("/", folder.getName());
	}

	// @Test
	// public void similiar() throws Exception {
	// String myControlXML =
	// "<struct><int>3</int><boolean>false</boolean></struct>";
	// String myTestXML =
	// "<struct><boolean>false</boolean><int>3</int></struct>";
	// Diff myDiff = new Diff(myControlXML, myTestXML);
	// assertTrue("XML similar " + myDiff.toString(),
	// myDiff.similar());
	// assertTrue("XML identical " + myDiff.toString(),
	// myDiff.identical());
	// }
}
