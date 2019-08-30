package net.douglashiura.leb.uid.scenario.servlet.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileName {
	public static final String EXTENSION = ".us";
	private String name;
	private String directory;
	private String separatorOut;
	private String separatorIn;
	public static final Pattern SEQUENCE = Pattern.compile("^[\\wW|.\\\\/]+$");

	public FileName(String path, Boolean isWindowsOS) throws NotAFileException {
		path = path.trim();
		if (path.isEmpty() || !path.contains(".")) {
			throw new NotAFileException("It is not a file!");
		}
		if (path.contains("/") && path.contains("\\")) {
			throw new NotAFileException("Mix of path separator");
		}
		if (!SEQUENCE.matcher(path).matches()) {
			throw new NotAFileException("Characters invalid");
		}

		separatorOut = isWindowsOS ? "\\" : "/";
		separatorIn = path.contains("/") ? "/" : "\\";
		if (path.endsWith(EXTENSION)) {
			path = path.substring(0, path.length() - EXTENSION.length());
		}
		name = extractFile(path);
		path = path.substring(0, path.length() - name.length());
		if (path.contains(".")) {
			path = path.replace(".", separatorIn);
		}
		if (!path.startsWith(separatorIn)) {
			path = separatorIn + path;
		}
		directory = path.trim();
		if (directory.contains(separatorIn)) {
			directory = directory.replace(separatorIn, separatorOut);
		}
	}

	public FileName(java.net.URI uri, Boolean isWindows) throws NotAFileException {
		this(uri.getPath(), isWindows);
	}

	public FileName(String packaged, String name, Boolean isWindowsOS) throws NotAFileException {
		this(mount(packaged, name), isWindowsOS);
	}

	private static String mount(String packaged, String name) {
		if (name.endsWith(EXTENSION)) {
			return packaged + "." + name;
		} else {
			return packaged + "." + name + EXTENSION;
		}
	}

	private String extractFile(String path) throws NotAFileException {
		if (path.contains(separatorIn)) {
			return path.substring(path.lastIndexOf(separatorIn) + 1);
		} else if (path.contains(".")) {
			return path.substring(path.lastIndexOf(".") + 1);

		} else {
			return path;
		}
	}

	public String getName() {
		return name + EXTENSION;
	}

	public String[] getPathsOfDirectory() {
		if (directory.startsWith(separatorOut)) {
			return split(directory.substring(1));
		} else {
			return split(directory);
		}
	}

	private String[] split(String substring) {
		List<String> directory = new ArrayList<>();
		while (!substring.isEmpty()) {
			int index = substring.indexOf(separatorOut);
			if (index > 0) {
				directory.add(substring.substring(0, index));
				substring = substring.substring(index+1);
			} else {
				directory.add(substring);
				substring = "";
			}
		}
		String[] array = new String[directory.size()];
		directory.toArray(array);
		return array;
	}

	public String getNameWithoutExtension() {
		return name;
	}

	public String getNameScenario() {
		return (directory + getName()).substring(1).replace(separatorOut, ".");
	}

}
