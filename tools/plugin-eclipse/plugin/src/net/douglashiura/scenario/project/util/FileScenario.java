package net.douglashiura.scenario.project.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import net.douglashiura.scenario.plugin.type.ExtractPahts;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.plugin.type.Scenario;
import net.douglashiura.scenario.plugin.view.Runner;
import net.douglashiura.scenario.project.Elements;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public class FileScenario {

	private IFile member;
	private List<Result> results;
	private Map<UUID, Rateable> elements = null;
	private byte[] text;
	private Notificable notificable;
	private List<Interaction> paths;
	private List<InteractionGeometry> interactions;
	private Scenario scenario;

	public FileScenario(IFile member) throws CoreException {
		this.member = member;
		this.results = new ArrayList<>();
		prepareScenario();
	}

	public String getName() {
		return member.getProjectRelativePath().toString();
	}

	private void prepareScenario() throws CoreException {
		text = read();
		scenario = new Scenario(new String(text));
		List<InteractionGeometry> starts = scenario.starts();
		paths = new ExtractPahts(starts).pathsOfExecution();
		elements = Elements.from(starts);
		interactions = scenario.getAllInteractions();
	}

	private byte[] read() throws CoreException {
		InputStream input = member.getContents();
		try {
			byte[] bytes = new byte[input.available()];
			input.read(bytes);
			return bytes;
		} catch (IOException e) {
			IStatus status = new Status(0, e.getMessage(), 0, e.getMessage(), e);
			throw new CoreException(status);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
			}
		}
	}

	public IProject getProject() {
		return member.getProject();
	}

	@Override
	public String toString() {
		return new String(text, StandardCharsets.UTF_8);
	}

	public void addResult(Result result) {
		if (!Results.END.equals(result.getResult())) {
			results.add(result);
			Rateable element = elements.get(result.getUuid());
			if (element != null) {
				element.setColor(result.getResult().getColor());
				notificationToView(element);
				if (notificable != null) {
					notificable.addResult(result, element);
				}
			}
		}
		if (Results.isExecutionFinishy(result.getResult())) {
			Results status = generalResult();
			notificable.finishyATestExecution(status);
			Runner.getRunner().updateStatusExecution(status);
		}
	}

	private Results generalResult() {
		for (Result result : results) {
			if (Results.ERROR.equals(result.getResult()) || Results.FAIL.equals(result.getResult())) {
				return result.getResult();
			}
		}
		return Results.OK;
	}

	private void notificationToView(Rateable rateable) {
		if (Runner.getRunner().getCurrent() == this) {
			Runner.getRunner().redraw(rateable);
		}

	}

	public void prepareToExecute() {
		if (elements != null) {
			Collection<Rateable> collection = elements.values();
			for (Rateable rateable : collection) {
				rateable.setColor(Results.UN_EXECUTED.getColor());
			}
			results.clear();
			notificationToView(null);
		}
	}

	public void setNotificable(Notificable itemScenario) {
		this.notificable = itemScenario;
	}

	public Collection<Rateable> getElementsCollection() {
		return elements.values();
	}

	public List<Interaction> getPaths() {
		return paths;
	}

	public List<InteractionGeometry> getAllInteractions() {
		return interactions;
	}

	public void save() throws CoreException {
		text = scenario.getJson();
		InputStream content = new ByteArrayInputStream(text);
		member.setContents(content, 0, null);
	}

	public Map<UUID, Rateable> getElements() {
		return elements;
	}

	public FileScenario selected() {
		for (Result result : results) {
			Rateable rateable = elements.get(result.getUuid());
			rateable.setColor(result.getResult().getColor());
		}
		return this;
	}

}
