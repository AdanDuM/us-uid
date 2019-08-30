package net.douglashiura.leb.uid.scenario.servlet.scenario;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.OnProject;
import net.douglashiura.leb.uid.scenario.data.OnUser;
import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.ProjectScenario;
import net.douglashiura.leb.uid.scenario.data.Scenario;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.ListenerConfigProjectScenario;
import net.douglashiura.leb.uid.scenario.servlet.util.FileName;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

public class OnContext {

	private HttpServletRequest request;

	public OnContext(HttpServletRequest request) {
		this.request = request;
	}

	public OnProject onProject() throws ProjectInvalidExeception, UserInvalidException, UserNameNullException,
			SimpleNameEmptyException, SimpleNameBiggerThat30Exception, SimpleNameInvalidException {
		return onUser().onProject(new SimpleName(getProject()));
	}

	public String getProject() {
		return request.getParameter("project");
	}

	public String getUser() {
		return request.getParameter("user");
	}

	public OnUser onUser() throws UserInvalidException, UserNameNullException, SimpleNameEmptyException,
			SimpleNameBiggerThat30Exception, SimpleNameInvalidException {
		return onProjectScenario().onUser(new SimpleName(getUser()));
	}

	public ProjectScenario onProjectScenario() {
		return (ProjectScenario) request.getServletContext().getAttribute(ListenerConfigProjectScenario.SCENARIO_APP);
	}

	public void setProject(HttpServletResponse resp) {
		resp.setHeader("user", getUser());
		resp.setHeader("project", getProject());
	}

	public void setFile(HttpServletResponse resp, FileName file) {
		resp.setHeader("scenario", file.getNameScenario());
	}

	public Scenario getScenario()
			throws FileNotFoundException, ProjectInvalidExeception, UserInvalidException, UserNameNullException,
			SimpleNameEmptyException, SimpleNameBiggerThat30Exception, SimpleNameInvalidException, NotAFileException {
		return onProject().getScenario(new FileName(getFile(), System.getProperty("os.name").startsWith("Windows")));
	}

	public String getFile() {
		return request.getParameter("scenario");
	}

}
