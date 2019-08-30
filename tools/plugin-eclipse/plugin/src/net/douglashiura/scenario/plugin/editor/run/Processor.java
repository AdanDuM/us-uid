package net.douglashiura.scenario.plugin.editor.run;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

import net.douglashiura.us.serial.InputFile;
import net.douglashiura.us.serial.Result;

public class Processor implements Runnable {

	private Executor executor;
	private ILaunch launch;
	private ServerSocket server;
	private Socket client;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Thread reader;
	private DefenderWithoutExecution defender;

	public Processor(IProject project, Executor executor, IProgressMonitor monitor) throws CoreException {
		this.executor = executor;
		try {
			server = new ServerSocket(0);
			ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
			ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
					String.format("User Scenario [%s]", server.getLocalPort()));
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, project.getName());
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "net.douglashiura.us.run.Executor");
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS,
					Integer.toString(server.getLocalPort()));
			ILaunchConfiguration configuration = wc.doSave();
			launch = configuration.launch(ILaunchManager.RUN_MODE, monitor);
			wc.delete();
			configuration.delete();
			defender = new DefenderWithoutExecution(launch, this);
			client = server.accept();
			output = new ObjectOutputStream(client.getOutputStream());
			input = new ObjectInputStream(client.getInputStream());
			reader = new Thread(this);
			reader.start();
		} catch (IOException e) {
			IStatus status = new Status(0, "net.douglashiura.scenario.plugin.editor.ScenarioView", e.getMessage(), e);
			throw new CoreException(status);
		}
	}

	public void write(InputFile scenario) throws IOException {
		output.writeObject(scenario);
		output.flush();
	}

	@Override
	public void run() {
		try {
			Result result;
			while ((result = (Result) input.readObject()) != null) {
				executor.delivery(result);
			}
		} catch (SocketException e) {
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			launch.terminate();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (reader != null) {
				reader.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			defender.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void exitIfSockIsOpen() {
		if (!server.isClosed()) {
			exit();
		}
	}
}
