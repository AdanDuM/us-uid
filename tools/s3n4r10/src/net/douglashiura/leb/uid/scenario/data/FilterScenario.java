package net.douglashiura.leb.uid.scenario.data;

import java.io.File;
import java.io.FilenameFilter;

import net.douglashiura.leb.uid.scenario.servlet.util.FileName;

public class FilterScenario implements FilenameFilter {

	

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(FileName.EXTENSION) && new File(dir, name).isFile();
	}

}
