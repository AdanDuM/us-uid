package net.douglashiura.leb.uid.scenario.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.douglashiura.leb.uid.scenario.data.ProjectInvalidExeception;
import net.douglashiura.leb.uid.scenario.data.Scenario;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameBiggerThat30Exception;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameEmptyException;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleNameInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserInvalidException;
import net.douglashiura.leb.uid.scenario.data.primitive.UserNameNullException;
import net.douglashiura.leb.uid.scenario.measures.uniform.Average;
import net.douglashiura.leb.uid.scenario.measures.uniform.Pair;
import net.douglashiura.leb.uid.scenario.measures.uniform.Pairs;
import net.douglashiura.leb.uid.scenario.servlet.scenario.OnContext;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

@WebServlet("/uniformity")
public class WebUniform extends HttpServlet {

	private static final long serialVersionUID = -2056786032626617198L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JsonObject uniform = new JsonObject();

		try {
			List<Scenario> allScenarios = new OnContext(request).onProject().listScenarios();
			List<Pair> pairs = Pairs.pairs(allScenarios);
			uniform.addProperty("average", new Average(pairs).getUniformity());
			mountByPairs(uniform, pairs);
			response.getWriter().append(uniform.toString());
		} catch (ProjectInvalidExeception | UserInvalidException | UserNameNullException | SimpleNameEmptyException
				| SimpleNameBiggerThat30Exception | SimpleNameInvalidException | NotAFileException e) {
			throw new ServletException(e);
		}
	}

	private void mountByPairs(JsonObject uniform, List<Pair> pairs) throws NotAFileException {
		JsonArray pairsJson = new JsonArray();
		for (Pair pair : pairs) {
			JsonObject element = new JsonObject();
			element.addProperty("from", pair.getA().getVirtualName());
			element.addProperty("to", pair.getB().getVirtualName());
			element.addProperty("uniformity", pair.getUniform().getRelativeUniformity().getUniformity());
			JsonObject absolute = new JsonObject();
			JsonObject uniformAbsolute = new JsonObject();
			JsonObject irregularAbsolute = new JsonObject();
			uniformAbsolute.addProperty("input", pair.getUniform().getUniformIntputs());
			uniformAbsolute.addProperty("output", pair.getUniform().getUniformOutputs());
			irregularAbsolute.addProperty("input", pair.getUniform().getNonUniforInputs());
			irregularAbsolute.addProperty("output", pair.getUniform().getNonUniformOutputs());
			absolute.add("uniform", uniformAbsolute);
			absolute.add("irregular", irregularAbsolute);
			element.add("absolute", absolute);
			pairsJson.add(element);
		}
		uniform.add("pairs", pairsJson);
	}
}