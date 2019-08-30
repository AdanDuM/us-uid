package net.douglashiura.leb.uid.scenario.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailBiggerThat120Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.EmailNullException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;

@WebServlet("/authentication")
public class WebAuthentication extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String USER = "user";

	private static final String PASSWORD = "password";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter(USER);
		Object password = request.getParameter(PASSWORD);

		try {
			Email email = new Email(username);
			for (User user : new OnContext(request).onProjectScenario().listUsers()) {
				if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
					response.getOutputStream().write(
							("{\"isError\":false, \"user\":\"" + user.getUsername().getName() + "\"}").getBytes());
					response.getOutputStream().flush();
					return;
				}
			}
		} catch (EmailEmptyException | EmailNullException | EmailBiggerThat120Exception | EmailInvalidException
				| UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException dontEmail) {
			try {
				SimpleName username2 = new SimpleName(username);
				for (User user : new OnContext(request).onProjectScenario().listUsers()) {
					if (user.getUsername().equals(username2) && user.getPassword().equals(password)) {
						response.getOutputStream().write(
								("{\"isError\":false, \"user\":\"" + user.getUsername().getName() + "\"}").getBytes());
						response.getOutputStream().flush();
						return;
					}
				}
			} catch (UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
					| SimpleNameInvalidException | EmailEmptyException | EmailNullException
					| EmailBiggerThat120Exception | EmailInvalidException error) {
			}
		}
		response.getOutputStream().write("{\"isError\":true}".getBytes());
		response.getOutputStream().flush();
	}
}