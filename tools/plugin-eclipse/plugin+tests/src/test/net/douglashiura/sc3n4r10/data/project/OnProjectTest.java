package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.OnProject;
import net.douglashiura.leb.uid.scenario.data.OnUser;
import net.douglashiura.leb.uid.scenario.data.ProjectDuplicationException;
import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.ProjectScenario;
import net.douglashiura.leb.uid.scenario.data.ProjectUnavailableException;
import net.douglashiura.leb.uid.scenario.data.Scenario;
import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.servlet.util.FileName;

public class OnProjectTest {

	private User douglas;
	private boolean isWindows;

	@Before
	public void setUp() {
		File defaultDir = new File(System.getProperty("user.home"), "us-uid");
		ProjectScenarioTest.deleteRecursive(defaultDir);
		isWindows = System.getProperty("os.name").startsWith("Windows");
	}

	@Test
	public void onUserEmpty() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		assertTrue(onUser.listProjects().isEmpty());
	}

	@Test
	public void onUser() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		File douglasDir = new File(scenario.getWorkDirectory(), douglas.getUsername().getName());
		assertEquals(1, onUser.listProjects().size());
		assertEquals(project, onUser.listProjects().get(0).getName());
		assertTrue(new File(douglasDir, project.getName()).exists());
	}

	@Test(expected = ProjectDuplicationException.class)
	public void createAProjectDuplication() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		onUser.createProject(project);
	}

	@Test
	public void createAProjectWhitoutScenarios() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		assertEquals(0, testProject.listScenarios().size());
		assertEquals(0, testProject.listScenariosAsNames().size());
	}

	@Test(expected = ProjectUnavailableException.class)
	public void renameProjectExist() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		testProject.createNewScenario(new FileName("br.net.Test.us", isWindows));
		testProject.rename(project);
	}

	@Test()
	public void renameProject() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		SimpleName project2 = new SimpleName("test2");
		testProject.createNewScenario(new FileName("br.net.Test.us", isWindows));
		testProject.rename(project2);
		OnProject testProject2 = onUser.onProject(project2);
		List<Scenario> listScenarios = testProject2.listScenarios();
		assertEquals(1, listScenarios.size());
		assertEquals(1, testProject2.listScenariosAsNames().size());
		assertEquals("", listScenarios.get(0).getDocument());
		assertEquals("br.net.Test.us", listScenarios.get(0).getVirtualName());
		assertEquals("br.net.Test.us", testProject2.listScenariosAsNames().get(0));
	}

	@Test(expected = ProjectInvalidExeception.class)
	public void renameProjectCheckOld() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		SimpleName project2 = new SimpleName("test2");
		testProject.createNewScenario(new FileName("br.net.Test.us", isWindows));
		testProject.rename(project2);
		onUser.onProject(project);
	}

	@Test(expected = ProjectInvalidExeception.class)
	public void deleteProject() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		testProject.delete();
		onUser.onProject(project);
	}

	@Test()
	public void deleteProjectAfterCreateAnDeleteScenario() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		FileName fileName= new FileName("br.net.douglashiura", "teste", isWindows);
		testProject.createNewScenario(fileName).delete();;
		testProject.delete();
		assertTrue(onUser.listProjects().isEmpty());
	}

	@Test()
	public void deleteProjectWithoutOnUser() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		testProject.delete();
		assertTrue(onUser.listProjects().isEmpty());
	}

	@Test
	public void createAScenarios() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.createProject(project);
		OnProject testProject = onUser.onProject(project);
		testProject.createNewScenario(new FileName("br.net.Test.us", isWindows));
		List<Scenario> listScenarios = testProject.listScenarios();
		assertEquals(1, listScenarios.size());
		assertEquals(1, testProject.listScenariosAsNames().size());
		assertEquals("", listScenarios.get(0).getDocument());
		assertEquals("br.net.Test.us", listScenarios.get(0).getVirtualName());
		assertEquals("br.net.Test.us", testProject.listScenariosAsNames().get(0));

	}

	@Test(expected = ProjectInvalidExeception.class)
	public void onProjectWithoutProject() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		SimpleName project = new SimpleName("test");
		onUser.onProject(project);
	}

}
