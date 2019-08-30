package net.douglashiura.leb.uid.scenario.servlet.scenario;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import net.douglashiura.leb.uid.scenario.data.DuplicationScenarioException;
import net.douglashiura.leb.uid.scenario.data.OnProject;
import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.util.FileName;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

@WebServlet("/scenario/update/*")
public class WebUpdate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			byte[] all = new byte[request.getContentLength()];
			IOUtils.readFully(request.getInputStream(), all);
			OnContext onContext = new OnContext(request);
			OnProject onProject = onContext.onProject();
			boolean isWindows = System.getProperty("os.name").startsWith("Windows");
			FileName fileName = new FileName(onContext.getFile(), isWindows);
			try {
				onProject.getScenario(fileName).delete();
			} catch (FileNotFoundException withoutFile) {
			}
			onProject.createNewScenario(fileName).write(all);
		} catch (NotAFileException | ProjectInvalidExeception | UserInvalidException | UserNameNullException
				| SimpleNameEmptyException | SimpleNameBiggerThat30Exception | SimpleNameInvalidException | DuplicationScenarioException e) {
			throw new ServletException(e);
		}
	}
}
