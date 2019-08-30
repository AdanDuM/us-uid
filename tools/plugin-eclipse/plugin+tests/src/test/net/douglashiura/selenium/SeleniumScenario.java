package test.net.douglashiura.selenium;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

public class SeleniumScenario {

	private static SeleniumScenario INSTANCE;

	private static final String FOLDER_DOWNLOAD = "/tmp/";
	private static final Integer TIMEOUT_MILISEGUNDOS = 1000 * 5;

	private static final int PAUSE = 1000;

	private FirefoxDriver selenium;

	public SeleniumScenario() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("linux")) {
			System.setProperty("webdriver.gecko.driver", "drivers/geckodriver-linux");
		} else {
			System.setProperty("webdriver.gecko.driver", "drivers/geckodriver-mac");
		}
		FirefoxProfile profile = createProfile();
		FirefoxOptions opcoes = new FirefoxOptions();
		opcoes.setProfile(profile);
		selenium = new FirefoxDriver(opcoes);
		selenium.manage().timeouts().implicitlyWait(TIMEOUT_MILISEGUNDOS, MILLISECONDS);
		selenium.manage().timeouts().pageLoadTimeout(TIMEOUT_MILISEGUNDOS, MILLISECONDS);
		selenium.manage().timeouts().setScriptTimeout(TIMEOUT_MILISEGUNDOS, MILLISECONDS);
		selenium.manage().window().maximize();
	}

	private static FirefoxProfile createProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.dir", FOLDER_DOWNLOAD);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/pdf, application/x-pdf, application/octet-stream, application/octet, text/html, text/csv, text/kml, application/vnd.google-earth.kml+xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("pdfjs.disabled", true);
		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", false);
		profile.setPreference("devtools.jsonview.enabled", false);
		profile.setPreference("dom.file.createInChild", true);
		return profile;
	}

	public static SeleniumScenario getInstance() {
		return INSTANCE == null ? INSTANCE = new SeleniumScenario() : INSTANCE;
	}

	public void go(String url) {
		selenium.get(url);
	}

	public void type(String id, String value) {
		selenium.findElement(By.id(id)).clear();
		selenium.findElement(By.id(id)).sendKeys(value);
	}

	public String getText(String id) {
		return selenium.findElement(By.id(id)).getText();
	}

	public String getPlaceholder(String id) {
		return selenium.findElement(By.id(id)).getAttribute("placeholder");
	}

	public String getUrl() {
		return selenium.getCurrentUrl();
	}

	public void click(String id) {
		selenium.findElement(By.id(id)).click();
	}

	public String getValue(String id) {
		return selenium.findElement(By.id(id)).getAttribute("value");
	}

	public void onMouse(String id) {
		Actions builder = new Actions(selenium);
		builder.moveToElement(selenium.findElement(By.id(id))).build().perform();
	}

	public void clean(String id) {
		selenium.findElement(By.id(id)).clear();

	}

	public boolean isVisible(String id) {
		return selenium.findElement(By.id(id)).isDisplayed();
	}

	public void pause() {
		try {
			Thread.sleep(PAUSE / 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shortPause() {
		try {
			Thread.sleep(PAUSE / 4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
