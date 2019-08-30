package net.douglashiura.leb.uid.scenario.measures.uniform;

public class RelativeUniformity {

	private Integer uniformInputs=0;
	private Integer uniformOutputs=0;
	private Integer nonUniformInputs=0;
	private Integer nonUniformOutputs=0;

	public Float getUniformity() {
		Integer uniform = uniformInputs + uniformOutputs;
		Integer nonUniform = nonUniformInputs + nonUniformOutputs;
		Integer total = uniform + nonUniform;
		if (total == 0)
			return 0f;
		return (uniform / (float) total);
	}

	public void setUniformInput(Integer uniformInputs) {
		this.uniformInputs = uniformInputs;
	}

	public void setUniformOutput(Integer uniformOutputs) {
		this.uniformOutputs = uniformOutputs;
	}

	public void setNonUniformInput(Integer nonUniformInputs) {
		this.nonUniformInputs = nonUniformInputs;
	}

	public void setNonUniformOutput(Integer nonUniformOutputs) {
		this.nonUniformOutputs = nonUniformOutputs;
	}

	public Integer getUniformInputs() {
		return uniformInputs;
	}

	public Integer getNonUniformInputs() {
		return nonUniformInputs;
	}

	public Integer getNonUniformOutputs() {
		return nonUniformOutputs;
	}

	public Integer getUniformOutputs() {
		return uniformOutputs;
	}

}
