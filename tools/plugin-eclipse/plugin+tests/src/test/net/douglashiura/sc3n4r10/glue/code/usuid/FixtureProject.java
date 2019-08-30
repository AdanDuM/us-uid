package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;

@Fixture("Project")
public class FixtureProject {
	private SeleniumScenario selenium;

	public FixtureProject()  {
		selenium = SeleniumScenario.getInstance();
	}

	public void setName(String value) {
		selenium.type("scenario", value);
	}

	public void setPackage(String value) {
		selenium.type("folder", value);
	}

	public void setScenarioInput(String value) {
		selenium.type("inputFunctions", value);
	}

	public String getSc3n4r10() {
		return selenium.getText("tool");
	}

	public String getNameValue() {
		return selenium.getValue("scenario");
	}

	public String getPackageValue() {
		return selenium.getValue("folder");
	}

	public String getLogout() {
		return selenium.getText("logout");
	}

	public String getUser() {
		return selenium.getText("user");
	}

	public String getProject() {
		return selenium.getText("project");
	}

	public String getUserGuide() {
		return selenium.getText("userGuide");
	}

	public String getClone() {
		return selenium.getText("clone");
	}

	public String getDelete() {
		return selenium.getText("delete");
	}

	public String getRename() {
		return selenium.getText("rename");
	}

	public String getEmptyScenario() {
		return selenium.getText("empty_scenario");
	}

	public String getName() {
		return selenium.getText("name");
	}

	public String getNewScenario() {
		return selenium.getText("new_scenario");
	}

	public String getPackage() {
		return selenium.getText("package");
	}

	public String getPlaceholderName() {
		return selenium.getPlaceholder("scenario");
	}

	public String getPlaceholderPackage() {
		return selenium.getPlaceholder("folder");
	}

	public String getScenario1(){
		return selenium.getText("scenario_1");
	}

	public String getScenario2() {
		return selenium.getText("scenario_2");
	}

	public String getScenarioInput() {
		return selenium.getValue("inputFunctions");
	}

	public String getTitleNewScenario() {
		return selenium.getText("new_scenario_title");
	}

	public String getScenarioUnavailable() {
		return selenium.getText("scenarioUnavailable");
	}

	public String getScenarioInvalid() {
		return selenium.getText("scenarioInvalid");
	}

	public String getNameInvalid() {
		return selenium.getText("scenarioInvalid");
	}

	public String getScenarioInputValue() {
		return selenium.getValue("inputFunctions");
	}

	public String getScenarioInputUnavailable() {
		return selenium.getText("nameInputUnavailable");
	}

	public String getScenarioInputInvalid() {
		return selenium.getText("nameInputInvalid");
	}

	public String getTitleUserScenario() {
		return selenium.getText("project_title");
	}

	public String getUrl() {
		return selenium.getUrl();
	}

	public void toProject(String action)  {
		if ("MouseOn(scenario:1)".equals(action)) {
			selenium.onMouse("new_scenario");
			selenium.onMouse("scenario_1");
			selenium.pause();
		} else if ("MouseOn(scenario:2)".equals(action)) {
			selenium.onMouse("down");
			selenium.onMouse("scenario_2");
			selenium.pause();
		} else if ("Delete(senario:1)".equals(action)) {
			selenium.click("delete");
		} else if ("Rename(scenario:1)".equals(action)) {
			selenium.click("rename");
		} else if ("Clone(scenario:1)".equals(action)) {
			selenium.click("clone");
		} else if ("/first".equals(action)) {
			selenium.click("project");
		} else if ("New scenario".equals(action)) {
			selenium.click("new_scenario");
		} else if ("Delete(scenario:2)".equals(action)) {
			selenium.click("delete");
		} else if ("Clone(scenario:2)".equals(action)) {
			selenium.click("clone");
		} else if ("Rename(scenario:2)".equals(action)) {
			selenium.click("rename");
			selenium.pause();
		}
		selenium.shortPause();
	}

	public void toProjects(String action) {
		if ("douglashiura".equals(action)) {
			selenium.click("user");
		}
	}

	public void toAuthentication(String action) {
		if ("Logout".equals(action)) {
			selenium.click("logout");
		}
	}

	public void toScenario(String action) {
		if (action.equals("New scenario")) {
			selenium.click("new_scenario");
		} else if ("Click(scenario:1)".equals(action)) {
			selenium.click("scenario_1");
		} else if ("Click(scenario:2)".equals(action)) {
			selenium.click("scenario_2");
		}
	}
}