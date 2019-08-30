package test.net.douglashiura.sample.help;

import net.douglashiura.us.Fixture;

@Fixture("CalculatorFixture2")
public class CalculatorFixture2 {

	public void setCommand(String name) throws InterruptedException {
		Thread.sleep(4000);
	}

	public String getDisplay() throws InterruptedException {
		Thread.sleep(4000);
		return null;
	}

	public void toCalculatorFixture3(String action) throws InterruptedException {
		Thread.sleep(100);
	}
}