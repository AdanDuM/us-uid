package test.net.douglashiura.sc3n4r10.glue.code.usuid;
import net.douglashiura.us.Fixture;
import test.net.douglashiura.selenium.SeleniumScenario;
@Fixture("Browser")
public class FixtureBrowser {
	public void setUrl(String url){
		SeleniumScenario.getInstance().go(url);
	}
	public void toAuthentication(String action){
	}
}