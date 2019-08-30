package net.douglashiura.leb.uid.scenario.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailBiggerThat120Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailNullException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;

public class ProjectScenario {

	public static final String OWNER_FILE_NAME = ".owner";
	private File workDirectory;

	public ProjectScenario() {
		this(getDefaultDir());
	}

	public ProjectScenario(File workDirectory) {
		this.workDirectory = workDirectory;
	}

	private static File getDefaultDir() {
		File defaultDir = new File(System.getProperty("user.home"), "us-uid");
		if (!defaultDir.exists()) {
			defaultDir.mkdir();
		}
		return defaultDir;
	}

	public File getWorkDirectory() {
		return workDirectory;
	}

	public void createUser(User user)
			throws IOException, UserAndEmailDuplicationException, UserDuplicationException, EmailDuplicationException {
		List<User> users;
		try {
			users = listUsers();
			for (User user2 : users) {
				if (user2.getEmail().equals(user.getEmail()) && user2.getUsername().equals(user.getUsername())) {
					throw new UserAndEmailDuplicationException();
				} else if (user2.getEmail().equals(user.getEmail())) {
					throw new EmailDuplicationException();
				} else if (user2.getUsername().equals(user.getUsername())) {
					throw new UserDuplicationException();
				}
			}
		} catch (EmailEmptyException | EmailNullException | EmailBiggerThat120Exception | EmailInvalidException
				| UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException e) {
			throw new RuntimeException("error in directory of users!");
		}
		File file = new File(workDirectory, user.getUsername().getName());
		if (file.exists()) {
			throw new UserDuplicationException();
		}
		file.mkdir();
		File fileOwner = new File(file, OWNER_FILE_NAME);
		fileOwner.createNewFile();
		FileOutputStream information = new FileOutputStream(fileOwner);
		information.write(user.toString().getBytes());
		information.flush();
		information.close();
	}

	public List<User> listUsers() throws IOException, EmailEmptyException, EmailNullException,
			EmailBiggerThat120Exception, EmailInvalidException, UserNameNullException, SimpleNameEmptyException,
			SimpleNameBiggerThat30Exception, SimpleNameInvalidException {
		List<User> users = new ArrayList<User>();
		for (File directory : workDirectory.listFiles()) {
			if (directory.isDirectory()) {
				File owner = new File(directory, ProjectScenario.OWNER_FILE_NAME);
				if (owner.exists()) {
					FileInputStream fileInputStream = new FileInputStream(owner);
					byte[] bytes = new byte[fileInputStream.available()];
					fileInputStream.read(bytes);
					fileInputStream.close();
					String[] content = new String(bytes).split("\n");
					String name = content[0].split("=")[1];
					String email = content[1].split("=")[1];
					String password = content[2].split("=")[1];
					users.add(new User(new Email(email), new SimpleName(name), password));
				}
			}
		}
		return users;
	}

	public OnUser onUser(User douglas) throws UserInvalidException {
		File workDirectoryOfUser = new File(workDirectory, douglas.getUsername().getName());
		if (!workDirectoryOfUser.exists()) {
			throw new UserInvalidException();
		}
		return new OnUser(workDirectoryOfUser);
	}

	public OnUser onUser(SimpleName username) throws UserInvalidException {
		return onUser(new User(null, username, null));
	}
}
