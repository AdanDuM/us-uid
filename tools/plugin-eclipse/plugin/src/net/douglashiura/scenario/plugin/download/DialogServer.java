package net.douglashiura.scenario.plugin.download;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DialogServer extends TitleAreaDialog {

	private static final String SERVER_FILE_NAME = ".serverus";

	private Text txtServer;

	private String server;

	private String user;

	private String project;

	private Text txtUser;

	private Text txtProject;

	private Text txtPassword;

	private String password;

	private Folder folder;

	public DialogServer(Folder folder) {
		super(null);
		this.folder = folder;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Server");
		setMessage("Enter with server URL", IMessageProvider.INFORMATION);
	}

	@Override
	protected org.eclipse.swt.widgets.Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		createServer(container);
		createProject(container);
		createUser(container);
		createPassword(container);
		loadOfFileIfExists();
		return area;
	}

	private void loadOfFileIfExists() {
		IFile aFile = folder.getFile(SERVER_FILE_NAME);
		if (aFile.exists()) {
			try {
				InputStream content = aFile.getContents();
				byte[] bytes = new byte[content.available()];
				content.read(bytes);
				String[] document = new String(bytes).split("\n");
				txtServer.setText(document[0]);
				txtProject.setText(document[1]);
				txtUser.setText(document[2]);
				txtPassword.setText(document[3]);
			} catch (CoreException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createPassword(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("Password");
		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;
		txtPassword = new Text(container, SWT.PASSWORD | SWT.BORDER);
		txtPassword.setLayoutData(dataFirstName);
	}

	private void createUser(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("User");
		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;
		txtUser = new Text(container, SWT.BORDER);
		txtUser.setText("name");
		txtUser.setLayoutData(dataFirstName);
	}

	private void createProject(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("Project");
		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;
		txtProject = new Text(container, SWT.BORDER);
		txtProject.setText("test");
		txtProject.setLayoutData(dataFirstName);
	}

	private void createServer(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("URL");
		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;
		txtServer = new Text(container, SWT.BORDER);
		txtServer.setText("http://localhost:8080/sc3n4r10/");
		txtServer.setLayoutData(dataFirstName);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		server = txtServer.getText();
		user = txtUser.getText();
		project = txtProject.getText();
		password = txtPassword.getText();
		IFile aFile = folder.getFile(SERVER_FILE_NAME);
		InputStream input = new ByteArrayInputStream(
				String.format("%s\n%s\n%s\n%s\n", server, user, project, password).getBytes());
		if (!aFile.exists()) {
			try {
				aFile.create(input, true, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			try {
				aFile.setContents(input, true, false, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getServer() {
		return server;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getProject() {
		return project;
	}

}