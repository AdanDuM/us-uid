package net.douglashiura.leb.uid.scenario.servlet.scenario;

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
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

/**
 * Servlet implementation class WebList
 */
@WebServlet("/scenario/get/*")
public class WebGetScenario extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setContentType("text/plain; charset=utf-8");
		try {
			response.getWriter().write(new OnContext(request).getScenario().getDocument());
		} catch (NotAFileException | ProjectInvalidExeception | UserInvalidException | UserNameNullException
				| SimpleNameEmptyException | SimpleNameBiggerThat30Exception | SimpleNameInvalidException e) {
			throw new ServletException(e);
		}
	}
}