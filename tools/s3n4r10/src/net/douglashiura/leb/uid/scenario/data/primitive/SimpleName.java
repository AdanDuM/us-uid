
package net.douglashiura.leb.uid.scenario.data.primitive;

import java.util.regex.Pattern;

public class SimpleName {

	public static final Pattern USERNAME = Pattern.compile("^[\\wW-]+$");

	private String name;

	public  SimpleName(String name) throws UserNameNullException, SimpleNameEmptyException, SimpleNameBiggerThat30Exception, SimpleNameInvalidException  {
		assertNotNull(name);
		this.name = name.trim();
		assertNotEmpty();
		assertEmailSizeLessThat120Caracters();
		assertSyntacticallyValid();
		 
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (objeto == null) {
			return false;
		}
		if (getClass() != objeto.getClass()) {
			return false;
		}
		SimpleName outro = (SimpleName) objeto;
		if (name == null) {
			if (outro.name != null) {
				return false;
			}
		} else if (!name.equals(outro.name)) {
			return false;
		}
		return true;
	}

	private void assertSyntacticallyValid() throws SimpleNameInvalidException {
		if (!USERNAME.matcher(name).matches()) {
			throw new SimpleNameInvalidException();
		}
	}

	private void assertEmailSizeLessThat120Caracters() throws SimpleNameBiggerThat30Exception {
		if (name.length() > SimpleNameBiggerThat30Exception.MAX) {
			throw new SimpleNameBiggerThat30Exception();
		}
	}

	private void assertNotEmpty() throws SimpleNameEmptyException {
		if (name.isEmpty()) {
			throw new SimpleNameEmptyException();
		}
	}

	private void assertNotNull(String email) throws UserNameNullException {
		if (email == null) {
			throw new UserNameNullException();
		}
	}

	public String getName() {
		return name;
	}
}
