package net.douglashiura.leb.uid.scenario.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.douglashiura.leb.uid.scenario.servlet.util.FileName;
import net.douglashiura.leb.uid.scenario.servlet.util.NotAFileException;

public class Scenario {

	private File file;
	private File defaultDir;
	private OnProject onProject;
	private Boolean isWindows;

	public Scenario(File file, File defaultDir, OnProject onProject) {
		this.file = file;
		this.defaultDir = defaultDir;
		this.onProject = onProject;
		isWindows = System.getProperty("os.name").startsWith("Windows");
	}

	public String getDocument() throws IOException {
		FileInputStream stream = new FileInputStream(file);
		byte[] b = new byte[stream.available()];
		stream.read(b);
		stream.close();
		return new String(b);
	}

	public void write(byte[] data) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		stream.write(data);
		stream.close();
	}

	public String getVirtualName() throws NotAFileException {
		return new FileName(file.getAbsolutePath().replace(defaultDir.getAbsolutePath(), ""), isWindows)
				.getNameScenario();
	}

	public void create() throws IOException {
		file.createNewFile();
		write("".getBytes());
	}

	public void rename(FileName fileName) throws IOException, DuplicationScenarioException {
		byte[] data = getDocument().getBytes();
		delete();
		onProject.createNewScenario(fileName).write(data);
	}

	public void delete() {
		file.delete();
	}

	public void clone(FileName file) throws IOException, DuplicationScenarioException {
		byte[] data = getDocument().getBytes();
		onProject.createNewScenario(file).write(data);
	}
}
