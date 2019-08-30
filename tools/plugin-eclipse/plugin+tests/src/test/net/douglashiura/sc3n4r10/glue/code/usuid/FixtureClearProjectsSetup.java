package test.net.douglashiura.sc3n4r10.glue.code.usuid;

import java.io.File;

import net.douglashiura.us.Fixture;
import test.net.douglashiura.sc3n4r10.data.project.ProjectScenarioTest;

@Fixture("ClearProjectsSetup")
public class FixtureClearProjectsSetup {
	public void toBrowser(String action) {
		File defaultDir = new File(System.getProperty("user.home"), "us-uid");
		for (File file : defaultDir.listFiles()) {
			ProjectScenarioTest.deleteRecursive(file);
		}
	}
}