package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;

@Fixture("Authentication")
public class FixtureAuthentication {

	private SeleniumScenario selenium;

	public FixtureAuthentication() {
		selenium = SeleniumScenario.getInstance();
	}

	public void setAuthenticationPassword(String value) {
		selenium.type("authentication_password", value);
	}

	public void setAuthenticationEmail(String value) {
		selenium.type("authentication_user", value);
	}

	public void setEmail(String value) {
		selenium.type("newAccount_email", value);
	}

	public void setPassword(String value) {
		selenium.type("newAccount_password", value);
	}

	public void setUser(String value) {
		selenium.type("newAccount_user", value);
	}

	public String getCreate() {
		return selenium.getText("createUser");
	}

	public String getSc3n4r10() {
		return selenium.getText("tool");
	}

	public String getPasswordValue() {
		return selenium.getValue("newAccount_password");
	}

	public String getEmail() {
		return selenium.getPlaceholder("newAccount_email");
	}

	public String getPassword() {
		return selenium.getPlaceholder("newAccount_password");
	}

	public String getEmailValue() {
		return selenium.getValue("newAccount_email");
	}

	public String getUserValue() {
		return selenium.getValue("newAccount_user");
	}

	public String getUrl() {
		return selenium.getUrl();
	}

	public String getUser() {
		return selenium.getPlaceholder("newAccount_user");
	}

	public String getUserGuide() {
		return selenium.getText("userGuide");
	}

	public String getTitleAuthentication() {
		return selenium.getText("authentication_title");
	}

	public String getEnterLogin() {
		return selenium.getText("authentication_enter");
	}

	public String getTitleNewAccount() {
		return selenium.getText("newAccount_title");
	}

	public String getPasswordLogin() {
		return selenium.getPlaceholder("authentication_password");
	}

	public String getUserLogin() {
		return selenium.getPlaceholder("authentication_user");
	}

	public String getAuthenticationError() {
		if (selenium.isVisible("errorAuthentication")) {
			return selenium.getText("errorAuthentication");
		}
		return "invisible";
	}

	public String getEmailInvalid() {
		if (selenium.isVisible("emailInvalid")) {
			return selenium.getText("emailInvalid");
		}
		return "invisible";
	}

	public String getEmailUnavailable() {
		if (selenium.isVisible("emailUnavailable")) {
			return selenium.getText("emailUnavailable");
		}
		return "invisible";
	}

	public String getUserInvalid() {
		if (selenium.isVisible("userInvalid")) {
			return selenium.getText("userInvalid");
		}
		return "invisible";
	}

	public String getUserUnavailable() {
		if (selenium.isVisible("userUnavailable")) {
			return selenium.getText("userUnavailable");
		}
		return "invisible";
	}

	public void toProjects(String action) {
		if ("Create".equals(action)) {
			selenium.click("createUser");
		} else if ("Enter".equals(action)) {
			selenium.click("authentication_enter");
		}
	}

	public void toUserGuide(String action) {
		selenium.click("userGuide");
	}

	public void toAuthentication(String action) {
		if ("Create".equals(action)) {
			selenium.click("createUser");
		} else if ("Enter".equals(action)) {
			selenium.click("authentication_enter");
		}

	}

}