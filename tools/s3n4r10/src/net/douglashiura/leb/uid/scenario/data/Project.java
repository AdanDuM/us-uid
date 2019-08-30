package net.douglashiura.leb.uid.scenario.data;

import java.io.File;

import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;

public class Project {

	private SimpleName name;

	public Project(File directory, SimpleName name) {
		this.name = name;
	}

	public SimpleName getName() {
		return name;
	}

}
