package net.douglashiura.scenario.plugin.upload;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.refresh.MonitorJob;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

import net.douglashiura.scenario.plugin.download.DialogServer;
import net.douglashiura.scenario.plugin.download.FileName;
import net.douglashiura.scenario.plugin.download.NotAFileException;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.scenario.project.util.Files;

public class UploadCommandHandler extends AbstractHandler implements ICoreRunnable {

	private DialogServer dialog;
	private Folder folder;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TreeSelection aEvent = (TreeSelection) HandlerUtil.getCurrentSelection(event);
		if (aEvent.getFirstElement() instanceof Folder
				&& ((Folder) aEvent.getFirstElement()).getParent() instanceof Project) {
			folder = (Folder) aEvent.getFirstElement();
			dialog = new DialogServer(folder);
			dialog.create();
			if (dialog.open() == Window.OK) {
				Job job = MonitorJob.create("Upload Scenarios to " + dialog.getServer(), this);
				job.schedule();
			}
		} else {
			MessageDialog.openInformation(null, "Fault", "Select a folder in a Java project!");
		}
		return null;
	}

	@Override
	public void run(IProgressMonitor monitor) throws CoreException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			List<FileScenario> files = Files.from(folder);
			int indexFile = 0;
			for (FileScenario file : files) {
				String fileName = file.getName().substring(folder.getProjectRelativePath().toString().length());
				monitor.worked(indexFile++);
				sendAFile(httpClient, file, fileName);
			}
		} catch (IOException | URISyntaxException | NotAFileException e) {
			IStatus status = new Status(0, "net.douglashiura.scenario.plugin.editor.ScenarioView", e.getMessage(), e);
			throw new CoreException(status);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		monitor.done();
	}

	private void sendAFile(CloseableHttpClient httpClient, FileScenario file, String fileName)
			throws URISyntaxException, NotAFileException, IOException, ClientProtocolException {
		URI uri2 = new URIBuilder(dialog.getURI("/scenario/update/"))
				.setParameter("scenario",
						new FileName(fileName, System.getProperty("os.name").startsWith("Windows")).getNameScenario())
				.build();
		HttpPost httpPost = new HttpPost(uri2);
		StringEntity entity = new StringEntity(file.toString(), StandardCharsets.UTF_8);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		CloseableHttpResponse response = httpClient.execute(httpPost);
		response.close();
	}

}