package net.douglashiura.leb.uid.scenario.servlet.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.OnUser;
import net.douglashiura.leb.uid.scenario.data.Project;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;

@WebServlet("/projects/*")
public class WebProjects extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			OnUser onUser = new OnContext(request).onUser();
			response.setContentType("text/plain; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			List<Project> projects = onUser.listProjects();
			StringBuilder json = new StringBuilder();
			json.append("[");
			int i = 0;
			for (Project folder : projects) {
				json.append("\"");
				json.append(folder.getName().getName());
				if (++i == projects.size()) {
					json.append("\"");
				} else {
					json.append("\", ");
				}
			}
			json.append("]");
			response.getWriter().write(json.toString());
		} catch (UserInvalidException | UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException error) {
			throw new ServletException(error);
		}
	}

}
