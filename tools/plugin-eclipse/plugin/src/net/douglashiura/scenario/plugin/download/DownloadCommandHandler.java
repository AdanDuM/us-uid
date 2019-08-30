package net.douglashiura.scenario.plugin.download;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.refresh.MonitorJob;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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

import com.google.gson.GsonBuilder;

public class DownloadCommandHandler extends AbstractHandler implements ICoreRunnable {

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
				Job job = MonitorJob.create("Download Scenarios from " + dialog.getServer(), this);
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
			URI server = dialog.getURI("scenarios/");
			HttpGet httpGet = new HttpGet(server);
			httpGet.setHeader("accept", "text/json");
			httpGet.setHeader("charset", StandardCharsets.UTF_8.name());
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			@SuppressWarnings("unchecked")
			List<String> elements = new GsonBuilder().create().fromJson(content, List.class);
			int indexFile = 0;
			httpGet.releaseConnection();
			for (String file : elements) {
				monitor.worked(indexFile++);
				getFile(monitor, httpClient, file);
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

	private void getFile(IProgressMonitor monitor, CloseableHttpClient httpClient, String file)
			throws URISyntaxException, IOException, ClientProtocolException, CoreException, NotAFileException {
		URI server = dialog.getURI("scenario/get/");
		URI uri = new URIBuilder(server).setParameter("scenario", file).build();
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("accept", "text/json");
		httpGet.setHeader("charset", StandardCharsets.UTF_8.name());
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		byte[] scenario = EntityUtils.toByteArray(entity);
		httpGet.releaseConnection();
		FileName fileName = new FileName(file,System.getProperty("os.name").startsWith("Windows"));
		IFolder aFolder = folder;
		for (String path : fileName.getPathsOfDirectory()) {
			aFolder = aFolder.getFolder(path);
			if (!aFolder.exists()) {
				aFolder.create(true, true, monitor);
			}
		}
		IFile aFile = aFolder.getFile(fileName.getName());
		if (aFile.exists()) {
			aFile.setContents(new ByteArrayInputStream(scenario), 0, monitor);
		} else {
			aFile.create(new ByteArrayInputStream(scenario), 0, monitor);
		}

	}

}