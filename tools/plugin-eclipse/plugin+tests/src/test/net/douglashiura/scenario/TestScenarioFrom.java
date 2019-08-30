package test.net.douglashiura.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import net.douglashiura.scenario.plugin.type.InputGeometry;
import net.douglashiura.scenario.plugin.type.InteractionGeometry;
import net.douglashiura.scenario.plugin.type.OutputGeometry;
import net.douglashiura.scenario.plugin.type.Scenario;

public class TestScenarioFrom {
	private String interacaoInicial = "{\"type\": \"br.ufsc.leb.uid.scenario.Interacao\","
			+ "\"id\": \"8c9987b0-3189-1087-2aba-9df20814018b\"," + "\"x\": 638," + "\"y\": 291," + "\"width\": 93,"
			+ "\"height\": 80," + "\"alpha\": 1," + "\"userData\": {" + "\"result\": \"\","
			+ "\"model\": \"Fixture8Puzzle\"" + "}," + "\"cssClass\": \"br_ufsc_leb_uid_scenario_Interacao\","
			+ "\"ports\": [" + "{" + "\"name\": \"output0\"," + "\"port\": \"draw2d.OutputPort\","
			+ "\"locator\": \"br.ufsc.leb.uid.scenario.ResourceLocator\"" + "}" + "]," + "\"bgColor\": \"#FFFFFF\","
			+ "\"color\": \"#000000\"," + "\"stroke\": 1," + "\"radius\": 500" + "}";

	private String interacaoAlvo = "{\"type\": \"br.ufsc.leb.uid.scenario.Interacao\",\"id\": \"6063b665-b963-62dd-de91-195d0d6791ad\","
			+ "\"x\": 626,\"y\": 156,\"width\": 113,\"height\": 83,\"alpha\": 1,\"userData\": {\"result\":\"\",\"model\": \"Alvo\"},"
			+ "\"cssClass\": \"br_ufsc_leb_uid_scenario_Interacao\",\"ports\": [{\"name\": \"input0\","
			+ "\"port\": \"draw2d.InputPort\",\"locator\": \"br.ufsc.leb.uid.scenario.TargetLocator\"}],\"bgColor\": \"#FFFFFF\","
			+ "\"color\": \"#000000\",\"stroke\": 1,\"radius\": 500 }";

	private String transaction = "{\"type\": \"br.ufsc.leb.uid.scenario.Transaction\","
			+ "\"id\": \"dcc6fc42-5fb4-d204-0d39-fbaf82c1cf85\"," + "\"alpha\": 1,\"userData\": {" + "\"result\": \"\","
			+ "\"model\": \"\" }," + "\"cssClass\": \"br_ufsc_leb_uid_scenario_Transaction\","
			+ "\"stroke\": 1,\"color\": \"#000000\",\"outlineStroke\": 0," + "\"outlineColor\": \"none\","
			+ "\"policy\": \"draw2d.policy.line.LineSelectionFeedbackPolicy\","
			+ "\"router\": \"draw2d.layout.connection.DirectRouter\"," + "\"radius\": 2,\"source\": {"
			+ "\"node\":\"8c9987b0-3189-1087-2aba-9df20814018b\"," + "\"port\": \"output0\" },\"target\": {"
			+ "\"node\": \"6063b665-b963-62dd-de91-195d0d6791ad\",\"port\":\"input0\"" + "}}";

	private String entrada = "{\"type\": \"br.ufsc.leb.uid.scenario.UserInput\",\"id\": \"deb51e97-bd3f-82c3-74d2-ef1815a42265\","
			+ "\"x\": 654,\"y\": 205,\"width\": 43,\"height\": 23,\"alpha\": 1,\"userData\": {\"result\": \"\",\"model\": \"entrada\""
			+ "},\"cssClass\": \"br_ufsc_leb_uid_scenario_UserInput\",\"composite\": \"6063b665-b963-62dd-de91-195d0d6791ad\","
			+ "\"ports\": [],\"bgColor\": \"none\",\"color\": \"#000000\",\"stroke\": 1,\"radius\": 0,"
			+ "\"text\": \"Valor\", \"outlineStroke\": 0,\"outlineColor\": \"none\",\"fontSize\": 12,"
			+ "\"fontColor\": \"#080808\",\"fontFamily\": null }";

	private String saida = "{\"type\": \"br.ufsc.leb.uid.scenario.SystemOutput\",\"id\": \"a2df2c45-be51-27cc-ae8d-f2772ceaa6f4\","
			+ "\"x\": 647,\"y\": 170,\"width\": 43,\"height\": 23, \"alpha\": 1,\"userData\": {\"result\": \"\","
			+ "\"model\": \"saida\"},\"cssClass\": \"br_ufsc_leb_uid_scenario_SystemOutput\",\"composite\": \"6063b665-b963-62dd-de91-195d0d6791ad\","
			+ "\"ports\": [],\"bgColor\": \"none\",\"color\": \"#FFFFFF\",\"stroke\": 1,\"radius\": 0,"
			+ "\"text\": \"Valor\",\"outlineStroke\": 0,\"outlineColor\": \"none\",\"fontSize\": 12,\"fontColor\": \"#000000\","
			+ "\"fontFamily\": null}";

	@Test
	public void interacaoComSaidaSistema() throws Exception {
		InteractionGeometry interacao = new Scenario(String.format("[%s,%s]", interacaoAlvo, saida)).starts().get(0);
		List<OutputGeometry> outputs = interacao.getOutputs();
		OutputGeometry output = outputs.get(0);
		assertEquals(626, interacao.getGeometry().getX().intValue());
		assertEquals(156, interacao.getGeometry().getY().intValue());
		assertEquals(113, interacao.getGeometry().getWidth().intValue());
		assertEquals(83, interacao.getGeometry().getHeight().intValue());
		assertEquals("Alvo", interacao.getFixtureName());
		assertEquals(UUID.fromString("6063b665-b963-62dd-de91-195d0d6791ad"), interacao.getId());
		assertNull(interacao.getTransaction());

		assertEquals(647, output.getGeometry().getX().intValue());
		assertEquals(170, output.getGeometry().getY().intValue());
		assertEquals(43, output.getGeometry().getWidth().intValue());
		assertEquals(23, output.getGeometry().getHeight().intValue());
		assertEquals(UUID.fromString("a2df2c45-be51-27cc-ae8d-f2772ceaa6f4"), output.getId());
		assertEquals("saida", output.getFixtureName());
		assertEquals("Valor", output.getValue());

	}

	@Test
	public void interacaoAlvo() throws Exception {
		InteractionGeometry interacao = new Scenario(String.format("[%s]", interacaoInicial)).starts().get(0);
		assertEquals(638, interacao.getGeometry().getX().intValue());
		assertEquals(291, interacao.getGeometry().getY().intValue());
		assertEquals(93, interacao.getGeometry().getWidth().intValue());
		assertEquals(80, interacao.getGeometry().getHeight().intValue());
		assertEquals("Fixture8Puzzle", interacao.getFixtureName());
		assertNull(interacao.getTransaction());
	}

	@Test
	public void interacaoComEntradaDeUsuario() throws Exception {
		InteractionGeometry interacao = new Scenario(String.format("[%s,%s]", interacaoAlvo, entrada)).starts().get(0);
		List<InputGeometry> inputs = interacao.getInputs();
		InputGeometry input = inputs.get(0);
		assertEquals(626, interacao.getGeometry().getX().intValue());
		assertEquals(156, interacao.getGeometry().getY().intValue());
		assertEquals(113, interacao.getGeometry().getWidth().intValue());
		assertEquals(83, interacao.getGeometry().getHeight().intValue());
		assertEquals("Alvo", interacao.getFixtureName());
		assertNull(interacao.getTransaction());
		assertEquals(654, input.getGeometry().getX().intValue());
		assertEquals(205, input.getGeometry().getY().intValue());
		assertEquals(43, input.getGeometry().getWidth().intValue());
		assertEquals(23, input.getGeometry().getHeight().intValue());
		assertEquals("entrada", input.getFixtureName());
		assertEquals(UUID.fromString("deb51e97-bd3f-82c3-74d2-ef1815a42265"), input.getId());
		assertEquals("Valor", input.getValue());
	}

	@Test
	public void interacaoSaidaEEntrada() throws Exception {
		InteractionGeometry interacao = new Scenario(
				String.format("[%s,%s,%s]", transaction, interacaoInicial, interacaoAlvo)).starts().get(0);
		InteractionGeometry target = interacao.getTransaction().getTargets().get(0).getTarget();
		assertNull(target.getTransaction());
		assertEquals(UUID.fromString("dcc6fc42-5fb4-d204-0d39-fbaf82c1cf85"), interacao.getTransaction().getUuid());
		assertEquals(638, interacao.getGeometry().getX().intValue());
		assertEquals(291, interacao.getGeometry().getY().intValue());
		assertEquals(93, interacao.getGeometry().getWidth().intValue());
		assertEquals(80, interacao.getGeometry().getHeight().intValue());
		assertEquals("Fixture8Puzzle", interacao.getFixtureName());
		assertEquals(626, target.getGeometry().getX().intValue());
		assertEquals(156, target.getGeometry().getY().intValue());
		assertEquals(113, target.getGeometry().getWidth().intValue());
		assertEquals(83, target.getGeometry().getHeight().intValue());
		assertEquals("Alvo", target.getFixtureName());
	}

}
