package net.douglashiura.us.run;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.douglashiura.us.Fixture;

public class Annotations {

	private static Map<String, Class<?>> map = new HashMap<String, Class<?>>();

	public static Class<?> getFixture(String name) throws IOException, ClassNotFoundException, URISyntaxException {
		Class<?> klass = map.get(name);
		if (klass == null) {
			scan();
			klass = map.get(name);
		}
		return klass;
	}

	private static Map<String, Class<?>> scan() throws IOException, ClassNotFoundException, URISyntaxException {
		List<Class<?>> all = getClassesAnnotatedWithFixture();
		for (Class<?> klass : all) {
			map.put(fixtureName(klass), klass);
		}
		return map;
	}

	private static List<Class<?>> getClassesAnnotatedWithFixture()
			throws IOException, ClassNotFoundException, URISyntaxException {
		List<Class<?>> klasses = getClasses();
		return klasses;
	}

	private static List<Class<?>> getClasses() throws ClassNotFoundException, IOException, URISyntaxException {
		Annotations.class.getClassLoader();
		List<Class<?>> classes = new ArrayList<>();
		Enumeration<URL> recursos = ClassLoader.getSystemResources("");
		while (recursos.hasMoreElements()) {
			URL url = (URL) recursos.nextElement();
			read(new File(url.toURI()), classes, url.getPath());
		}
		return classes;
	}

	private static void read(File file, List<Class<?>> classes, String relative)
			throws ClassNotFoundException, IOException {
		Class<?> classe;
		if (file.isFile() && ((classe = getCLassAnnotation(file, relative)) != null))
			classes.add(classe);
		if (file.isDirectory())
			for (File filho : file.listFiles())
				read(filho, classes, relative);
	}

	private static Class<?> getCLassAnnotation(File file, String relative) throws ClassNotFoundException, IOException {
		if (file.getName().endsWith(".class")) {
			String name = file.getAbsolutePath().replace(relative, "").replace(".class", "").replace("/", ".");
			try {
				Class<?> klass = Class.forName(name);
				if (klass.getAnnotation(Fixture.class) != null)
					return klass;
			} catch (SecurityException e) {
				return null;
			}
		}
		return null;
	}

	private static String fixtureName(Class<?> klass) {
		String name = klass.getAnnotation(Fixture.class).value();
		if (name.isEmpty())
			name = klass.getSimpleName();
		return name;
	}
}