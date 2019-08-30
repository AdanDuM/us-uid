package test.net.douglashiura.sc3n4r10.measures.uniform;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.douglashiura.leb.uid.scenario.measures.uniform.RelativeUniformity;

public class TesteRelativeUniformity {

	private RelativeUniformity relative;
	private Integer outputs;
	private Integer inputs;
	private Integer nonUniformOutputs;
	private Integer nonUniformInputs;

	@Before
	public void setUp() {
		relative = new RelativeUniformity();
		outputs = 1;
		inputs = 2;
		nonUniformOutputs = 2;
		nonUniformInputs = 1;
	}

	@Test
	public void zeroPercent() throws Exception {
		assertEquals(Float.valueOf(0), relative.getUniformity());
	}

	@Test
	public void uniformInput() {
		relative.setUniformInput(inputs);
		assertEquals(Float.valueOf(1f), relative.getUniformity());
	}

	@Test
	public void uniformOutput() {
		relative.setUniformOutput(outputs);
		assertEquals(Float.valueOf(1f), relative.getUniformity());
	}

	@Test
	public void uniformOutputNonUniformInputs() {
		relative.setUniformOutput(outputs);
		relative.setNonUniformInput(nonUniformInputs);
		assertEquals(Float.valueOf(0.5f), relative.getUniformity());
	}

	@Test
	public void uniformOutputNonUniformOutputs() {
		relative.setUniformOutput(outputs);
		relative.setNonUniformOutput(nonUniformOutputs);
		assertEquals(Float.valueOf(0.3333333333334f), relative.getUniformity());
	}

	@Test
	public void uniformInputsNonUniformInputs() {
		relative.setUniformInput(inputs);
		relative.setNonUniformInput(nonUniformInputs);
		assertEquals(Float.valueOf(0.6666667f), relative.getUniformity());
	}

	@Test
	public void all() throws Exception {
		relative.setUniformInput(inputs);
		relative.setNonUniformInput(nonUniformInputs);
		relative.setUniformOutput(outputs);
		relative.setNonUniformOutput(nonUniformOutputs);
		assertEquals(Float.valueOf(0.5f), relative.getUniformity());
	}

	@Test
	public void nonUniformOutput() {
		relative.setNonUniformOutput(nonUniformOutputs);
		assertEquals(Float.valueOf(0f), relative.getUniformity());
	}

	@Test
	public void nonUniformInputs() {
		relative.setNonUniformInput(nonUniformInputs);
		assertEquals(Float.valueOf(0f), relative.getUniformity());
	}

	@Test
	public void hundredPercent() throws Exception {
		relative.setUniformInput(inputs);
		relative.setUniformOutput(outputs);
		assertEquals(Float.valueOf(1f), relative.getUniformity());
	}

	@Test
	public void zeroPercentOfNonUniform() throws Exception {
		relative.setNonUniformInput(nonUniformInputs);
		relative.setNonUniformOutput(nonUniformOutputs);
		assertEquals(Float.valueOf(0f), relative.getUniformity());
	}
}