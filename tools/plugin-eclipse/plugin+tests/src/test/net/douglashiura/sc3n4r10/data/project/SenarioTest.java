package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.DuplicationScenarioException;
import net.douglashiura.leb.uid.scenario.data.OnProject;
import net.douglashiura.leb.uid.scenario.data.OnUser;
import net.douglashiura.leb.uid.scenario.data.ProjectScenario;
import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.servlet.util.FileName;

public class SenarioTest {

	private User douglas;
	private OnProject onProject;
	private boolean isWindows;

	@Before
	public void setUp() throws Exception {
		File defaultDir = new File(System.getProperty("user.home"), "us-uid");
		ProjectScenarioTest.deleteRecursive(defaultDir);
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		onProject = onUser.onProject(project);
		isWindows = System.getProperty("os.name").startsWith("Windows");
	}

	@Test(expected = FileNotFoundException.class)
	public void getScenarioNotFound() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.getScenario(test);
	}

	@Test
	public void getScenario() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		assertEquals("", onProject.getScenario(test).getDocument());
	}

	@Test(expected=DuplicationScenarioException.class)
	public void createDuplicationScenario() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.createNewScenario(test);
	}

	@Test
	public void getScenarioWrite() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.getScenario(test).write("[]".getBytes());
		assertEquals("[]", onProject.getScenario(test).getDocument());
	}

	@Test(expected = FileNotFoundException.class)
	public void getScenarioDelete() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.getScenario(test).delete();
		onProject.getScenario(test);
	}

	@Test
	public void getScenarioRename() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.getScenario(test).write("[]".getBytes());
		FileName testing = new FileName("net.douglas.Testing", isWindows);
		onProject.getScenario(test).rename(testing);
		assertEquals("[]", onProject.getScenario(testing).getDocument());
	}

	@Test(expected = FileNotFoundException.class)
	public void getScenarioRenameDeleteOriginal() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.getScenario(test).write("[]".getBytes());
		FileName testing = new FileName("net.douglas.Testing", isWindows);
		onProject.getScenario(test).rename(testing);
		onProject.getScenario(test);
	}

	@Test
	public void cloneScenario() throws Exception {
		FileName test = new FileName("net.douglashiura.Test", isWindows);
		onProject.createNewScenario(test);
		onProject.getScenario(test).write("[]".getBytes());
		FileName testing = new FileName("net.douglas.Testing", isWindows);
		onProject.getScenario(test).clone(testing);
		assertEquals("[]", onProject.getScenario(testing).getDocument());
		assertEquals("[]", onProject.getScenario(test).getDocument());
	}

}
