package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;

@Fixture("UserGuide")
public class FixtureUserGuide {
	public String getUrl() {
		return SeleniumScenario.getInstance().getUrl();
	}
}