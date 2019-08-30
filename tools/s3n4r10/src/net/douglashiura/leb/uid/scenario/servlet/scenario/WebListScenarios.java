package net.douglashiura.leb.uid.scenario.servlet.scenario;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.DuplicationScenarioException;
import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.Scenario;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.util.FileName;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;
@WebServlet("/scenarios/*")
public class WebListScenarios extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "scenario";
	private static final String FOLDER = "folder";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		boolean isWindows = System.getProperty("os.name").startsWith("Windows");
		String folder = request.getParameter(FOLDER);
		String name = request.getParameter(NAME).toString();
		boolean nameUnavailable = false;
		boolean nameInvalid = false;
		FileName file = null;
		try {
			file = new FileName(folder, name, isWindows);
		} catch (NotAFileException e1) {
			nameInvalid = true;
		}

		if (!nameInvalid) {
			try {
				OnContext onContext = new OnContext(request);
				onContext.onProject().createNewScenario(file);
			} catch (ProjectInvalidExeception | UserInvalidException | UserNameNullException | SimpleNameEmptyException
					| SimpleNameBiggerThat30Exception | SimpleNameInvalidException e) {
				throw new ServletException(e);
			} catch (DuplicationScenarioException e) {
				nameUnavailable = true;
			}
		}
		resp.getOutputStream()
				.write(errors(nameInvalid, nameUnavailable, file == null ? null : file.getNameScenario()));
	}

	private byte[] errors(boolean nameInvalid, boolean nameUnavailable, String scenario) {
		return ("{\"nameInvalid\":" + nameInvalid + ",\"nameUnavailable\":" + nameUnavailable + ", \"scenario\":\""
				+ scenario + "\"}").getBytes();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setContentType("text/plain; charset=utf-8");
		try {
			List<Scenario> scenarios = new OnContext(request).onProject().listScenarios();
			StringBuilder json = new StringBuilder();
			json.append("[");
			int i = 0;
			for (Scenario scenario : scenarios) {
				json.append("\"");
				json.append(scenario.getVirtualName());
				if (++i == scenarios.size()) {
					json.append("\"");
				} else {
					json.append("\", ");
				}
			}
			json.append("]");
			response.getWriter().write(json.toString());
		} catch (ProjectInvalidExeception | UserInvalidException | UserNameNullException | SimpleNameEmptyException
				| SimpleNameBiggerThat30Exception | SimpleNameInvalidException | NotAFileException e) {
			throw new ServletException(e);
		}
	}
}