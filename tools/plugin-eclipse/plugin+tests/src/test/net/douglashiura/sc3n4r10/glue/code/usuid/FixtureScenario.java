package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;

@Fixture("Scenario")
public class FixtureScenario {

	private SeleniumScenario selenium;

	public FixtureScenario() {
		selenium = SeleniumScenario.getInstance();
	}

	public String getFuncionalData() {
		return selenium.getText("propertyHeader");
	}

	public String getInput() {
		return selenium.getText("userInput");
	}

	public String getOutput() {
		return selenium.getText("systemOutput");
	}

	public String getProgressive() {
		return selenium.getText("stateProgressive");
	}

	public String getRegressive() {
		return selenium.getText("stateRegressive");
	}

	public String getSave() {
		return selenium.getText("save");
	}

	public String getTool() {
		return selenium.getText("project");
	}

	public String getUniformity() {
		return selenium.getText("spanUniformity");
	}

	public String getUrl() {
		return selenium.getUrl();
	}

	public void toProject(String action) {
		selenium.click("project");
	}
}