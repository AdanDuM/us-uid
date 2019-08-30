package net.douglashiura.leb.uid.scenario.servlet.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;

@WebServlet("/project/delete/*")
public class WebDeleteProject extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			new OnContext(request).onProject().delete();
			response.setContentType("text/plain");
		} catch (ProjectInvalidExeception | UserInvalidException | UserNameNullException | SimpleNameEmptyException
				| SimpleNameBiggerThat30Exception | SimpleNameInvalidException e) {
			throw new ServletException(e);
		}
	}

}
