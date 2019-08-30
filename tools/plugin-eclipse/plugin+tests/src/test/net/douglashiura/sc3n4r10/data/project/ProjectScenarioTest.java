package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.EmailDuplicationException;
import net.douglashiura.leb.uid.scenario.data.OnUser;
import net.douglashiura.leb.uid.scenario.data.ProjectScenario;
import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.UserAndEmailDuplicationException;
import net.douglashiura.leb.uid.scenario.data.UserDuplicationException;
import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;

public class ProjectScenarioTest {

	private User douglas;

	@Before
	public void setUp() {
		File defaultDir = new File(System.getProperty("user.home"), "us-uid");
		deleteRecursive(defaultDir);
	}

	public static void deleteRecursive(File defaultDir) {
		File[] dirs = defaultDir.listFiles();
		if (dirs != null) {
			for (File file : dirs) {
				if (file.isDirectory()) {
					deleteRecursive(file);
				} else {
					file.delete();
				}
			}
		}
		defaultDir.delete();
	}

	@Test
	public void empytDir() throws Exception {
		assertFalse(new File(System.getProperty("user.home"), "us-uid").exists());
	}

	@Test
	public void createDefault() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		assertEquals(new File(System.getProperty("user.home"), "us-uid"), scenario.getWorkDirectory());
		assertTrue(scenario.getWorkDirectory().exists());
		assertTrue(scenario.listUsers().isEmpty());
	}

	@Test
	public void createRoot() throws Exception {
		ProjectScenario scenario = new ProjectScenario(File.listRoots()[0]);
		assertEquals(File.listRoots()[0], scenario.getWorkDirectory());
	}

	@Test
	public void createUser() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		File userDir = new File(scenario.getWorkDirectory(), douglas.getUsername().getName());
		FileInputStream owner = new FileInputStream(new File(userDir, ".owner"));
		byte[] bytes = new byte[owner.available()];
		owner.read(bytes);
		owner.close();
		List<User> listUsers = scenario.listUsers();
		assertEquals(1, listUsers.size());
		assertEquals(douglas.toString(), new String(bytes));
		assertEquals(douglas.getUsername(), listUsers.get(0).getUsername());
		assertEquals(douglas.getPassword(), listUsers.get(0).getPassword());
		assertEquals(douglas.getEmail(), listUsers.get(0).getEmail());
		assertEquals(douglas, listUsers.get(0));
	}

	@Test(expected = UserAndEmailDuplicationException.class)
	public void userAndDuplicate() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		scenario.createUser(douglas);
	}

	@Test(expected = EmailDuplicationException.class)
	public void emailDuplicate() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		douglas.setUser(new SimpleName("doug"));
		scenario.createUser(douglas);
	}

	@Test(expected = UserDuplicationException.class)
	public void userDuplicate() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		douglas.setEmail(new Email("douglas@gmail.com"));
		scenario.createUser(douglas);
	}

	@Test
	public void onUser() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas);
		assertTrue(onUser.listProjects().isEmpty());
	}

	@Test
	public void onUserByUsername() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.createUser(douglas);
		OnUser onUser = scenario.onUser(douglas.getUsername());
		assertTrue(onUser.listProjects().isEmpty());
	}

	@Test(expected = UserInvalidException.class)
	public void onUserByUsernameNotCreated() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.onUser(douglas.getUsername());
	}

	@Test(expected = UserInvalidException.class)
	public void onUserNotCreated() throws Exception {
		ProjectScenario scenario = new ProjectScenario();
		scenario.onUser(douglas);
	}

}
