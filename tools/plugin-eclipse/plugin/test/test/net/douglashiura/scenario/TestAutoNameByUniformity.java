//package test.net.douglashiura.scenario;
//
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.eclipse.core.resources.IFile;
//import org.junit.jupiter.api.Test;
//
//import com.google.gson.internal.LinkedTreeMap;
//
//import net.douglashiura.scenario.plugin.type.Geometry;
//import net.douglashiura.scenario.plugin.type.InteractionGeometry;
//import net.douglashiura.scenario.project.FileInteraction;
//import net.douglashiura.scenario.project.util.AutoNameByUniformity;
//import net.douglashiura.scenario.project.util.FileScenario;
//
//public class TestAutoNameByUniformity {
//
//	@Test
//	public void empty() throws Exception {
//		List<FileInteraction> elements = new ArrayList<FileInteraction>();
//		new AutoNameByUniformity(elements).naming();
//	}
//
//	@Test
//	public void one() throws Exception {
//		fail("to continue ...");
//		List<FileInteraction> elements = new ArrayList<FileInteraction>();
//		LinkedTreeMap<String, ?> objectJson = null;
//		UUID id = null;
//		Geometry geometry = null;
//		String model = null;
//		InteractionGeometry interaction = new InteractionGeometry(objectJson, id, geometry, model);
//		IFile member = new IFileMock();
//		FileScenario fileScenario = new FileScenario(member);
//		FileInteraction file = new FileInteraction(fileScenario, interaction);
//		elements.add(file);
//		new AutoNameByUniformity(elements).naming();
//	}
//}