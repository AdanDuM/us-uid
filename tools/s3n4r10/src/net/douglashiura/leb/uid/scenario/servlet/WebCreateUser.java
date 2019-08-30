package net.douglashiura.leb.uid.scenario.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.douglashiura.leb.uid.scenario.data.EmailDuplicationException;
import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.UserAndEmailDuplicationException;
import net.douglashiura.leb.uid.scenario.data.UserDuplicationException;
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

@WebServlet("/newAccount/*")
public class WebCreateUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String USER = "user";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user=null;
		boolean emailInvalid = false;
		boolean userInvalid = false;
		boolean userUnavailable = false;
		boolean emailUnavailable = false;
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String password = request.getParameter(PASSWORD);

		Email email = null;
		try {
			email = new Email(request.getParameter(EMAIL));
		} catch (EmailEmptyException | EmailNullException | EmailBiggerThat120Exception | EmailInvalidException e) {
			emailInvalid = true;
		}
		SimpleName username = null;
		try {
			username = new SimpleName(request.getParameter(USER));
		} catch (UserNameNullException | SimpleNameEmptyException | SimpleNameBiggerThat30Exception
				| SimpleNameInvalidException e) {
			userInvalid = true;
		}
		if (userInvalid || emailInvalid) {
			response.getOutputStream().write(errors( userInvalid,  userUnavailable,  emailUnavailable,  emailInvalid,  user));
			response.getOutputStream().flush();
			return;
		} else {
			try {
				new OnContext(request).onProjectScenario().createUser(new User(email, username, password));
				user = username.getName();
			} catch (UserAndEmailDuplicationException error) {
				userUnavailable = true;
				emailUnavailable = true;
			} catch (UserDuplicationException e) {
				userUnavailable = true;
			} catch (EmailDuplicationException e) {
				emailUnavailable = true;
			}
		}
		response.getOutputStream().write(errors( userInvalid,  userUnavailable,  emailUnavailable,  emailInvalid,  user));
		response.getOutputStream().flush();
	}

	private byte[] errors(boolean userInvalid, boolean userUnavailable, boolean emailUnavailable, boolean emailInvalid, String user) {
		return ("{\"userInvalid\":" + userInvalid + ",\"userUnavailable\":" + userUnavailable + ",\"emailUnavailable\":"
				+ emailUnavailable + ",\"emailInvalid\":" + emailInvalid + ", \"user\":\"" + user + "\"}").getBytes();
	}
}