package net.douglashiura.leb.uid.scenario.servlet.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.ProjectDuplicationException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;

@WebServlet("/newProject/*")
public class WebCreateProject extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "name";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		boolean nameInvalid = false;
		boolean nameUnavailable = false;
		String name = request.getParameter(NAME);
		SimpleName project = null;
		try {
			project = new SimpleName(name);
		} catch (UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException e) {
			nameInvalid = true;
		}
		if (!nameInvalid) {
			try {
				new OnContext(request).onUser().createProject(project);
			} catch (UserInvalidException | UserNameNullException | SimpleNameEmptyException
					| SimpleNameBiggerThat30Exception | SimpleNameInvalidException error) {
				throw new ServletException(error);
			} catch (ProjectDuplicationException e) {
				nameUnavailable = true;
			}
		}
		resp.getOutputStream().write(errors(nameInvalid, nameUnavailable, project == null ? null : project.getName()));
	}

	private byte[] errors(boolean nameInvalid, boolean nameUnavailable, String project) {
		return ("{\"nameInvalid\":" + nameInvalid + ",\"nameUnavailable\":" + nameUnavailable + ", \"project\":\""
				+ project + "\"}").getBytes();
	}
}