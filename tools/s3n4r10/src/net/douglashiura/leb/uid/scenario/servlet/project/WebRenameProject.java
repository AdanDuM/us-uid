package net.douglashiura.leb.uid.scenario.servlet.project;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.ProjectUnavailableException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;
import net.douglashiura.leb.uid.scenario.servlet.util.Command;

@WebServlet("/project/rename/*")
public class WebRenameProject extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean nameUnavailable = false;
		boolean nameInvalid = false;
		Gson gson = new GsonBuilder().create();
		Command command = gson.fromJson(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8),
				Command.class);
		SimpleName name = null;
		try {
			name = new SimpleName(command.getNewFile());
		} catch (UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException e1) {
			nameInvalid = true;
		}
		if (!nameInvalid) {
			try {
				new OnContext(request).onProject().rename(name);
				response.setContentType("text/plain");
			} catch (ProjectInvalidExeception | UserInvalidException | UserNameNullException | SimpleNameEmptyException
					| SimpleNameBiggerThat30Exception | SimpleNameInvalidException e) {
				throw new ServletException(e);
			} catch (ProjectUnavailableException e) {
				nameUnavailable = true;
			}
		}
		response.getOutputStream().write(errors(nameInvalid, nameUnavailable));
	}

	private byte[] errors(boolean nameInvalid, boolean nameUnavailable) {
		return ("{\"nameInvalid\":" + nameInvalid + ",\"nameUnavailable\":" + nameUnavailable + "}").getBytes();
	}

}
