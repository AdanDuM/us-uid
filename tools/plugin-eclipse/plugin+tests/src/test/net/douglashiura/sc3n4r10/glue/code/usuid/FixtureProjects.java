package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;

@Fixture("Projects")
public class FixtureProjects {

	private SeleniumScenario selenium;

	public FixtureProjects() {
		selenium = SeleniumScenario.getInstance();
	}

	public void setInputProject(String value) {
		selenium.type("inputFunctions", value);
	}

	public void setName(String value) {
		selenium.type("project_name", value);
	}

	public String getCreate() {
		return selenium.getText("project_create");
	}

	public String getSc3n4r10() {
		return selenium.getText("tool");
	}

	public String getLogout() {
		return selenium.getText("logout");
	}

	public String getUser() {
		return selenium.getText("user");
	}

	public String getEmptyProject() {
		return selenium.getText("empty_projects");
	}

	public String getName() {
		return selenium.getText("name_label");
	}

	public String getNameValue() {
		return selenium.getValue("project_name");
	}

	public String getPlaceholderName() {
		return selenium.getPlaceholder("project_name");
	}

	public String getTitleProject() {
		return selenium.getText("project_title");
	}

	public String getTitleProjects() {
		return selenium.getText("projects_title");
	}

	public String getInputProject() {
		return selenium.getValue("inputFunctions");
	}

	public String getProject1() {
		return selenium.getText("project_1");
	}

	public String getProject2() {
		return selenium.getText("project_2");
	}

	public String getRename() {
		return selenium.getText("rename");
	}

	public String getDelete() {
		return selenium.getText("delete");
	}

	public String getNameUnavailable() {
		return selenium.getText("nameUnavailable");
	}

	public String getInputInvalid() {
		return selenium.getText("nameInputInvalid");
	}

	public String getInputUnavailable() {
		return selenium.getText("nameInputUnavailable");
	}

	public String getUrl() {
		return selenium.getUrl();
	}

	public String getUserGuide() {
		return selenium.getText("userGuide");
	}

	public String getErroName() {
		return selenium.getText("nameInvalid");
	}

	public void toUserGuide(String action) {
		if ("UserGuide".equals(action)) {
			selenium.click("userGuide");
		}
	}

	public void toProject(String action) {
		if ("Create".equals(action)) {
			selenium.click("project_create");
		}
	}

	public void toProjects(String action) {
		if ("douglashiura".equals(action)) {
			selenium.click("user");
		} else if ("MouseOn(project:1)".equals(action)) {
			selenium.onMouse("userGuide");
			selenium.onMouse("project_1");
			selenium.pause();
		} else if ("MouseOn(project:2)".equals(action)) {
			selenium.onMouse("down");
			selenium.onMouse("project_2");
			selenium.pause();
		} else if ("Delete(project:1)".equals(action)) {
			selenium.click("delete");
		} else if ("Rename(project:1)".equals(action)) {
			selenium.click("rename");
			selenium.pause();
		} else if ("Create".equals(action)) {
			selenium.click("project_create");
		} else if ("Rename(project:2)".equals(action)) {
			selenium.click("rename");
			selenium.pause();
		} else if ("Rename(project:1)-duplication".equals(action)) {
			selenium.click("rename");
			selenium.pause();
		}

	}

	public void toAuthentication(String action) {
		if ("Logout".equals(action)) {
			selenium.click("logout");
		}
	}

}