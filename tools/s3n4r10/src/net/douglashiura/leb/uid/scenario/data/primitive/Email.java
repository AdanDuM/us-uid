package net.douglashiura.leb.uid.scenario.data.primitive;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DOMAIN = "[0-9a-z.-]+\\.[a-z]{2,7}$";
	private static final String USER = "^[\\w-]+(\\.[\\w-]+)*";
	public static final Pattern FULL_EMAIL = Pattern.compile(USER + "@" + DOMAIN);
	public static final Pattern USER_OF_EMAIL = Pattern.compile(USER + "$");
	public static final Pattern PATTER_DOMAIN = Pattern.compile("^" + DOMAIN);

	private String email;
	private String domain;
	private String user;

	public Email(String email)
			throws EmailEmptyException, EmailNullException, EmailBiggerThat120Exception, EmailInvalidException {
		assertNotNull(email);
		this.email = email.trim().toLowerCase();
		assertNotEmpty();
		assertEmailSizeLessThat120Caracters();
		assertSintaticalyValid();
		split();
	}

	private void split() {
		String[] partes = email.split("@");
		user = partes[0];
		domain = partes[1];
	}

	@Override
	public String toString() {
		return email;
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
		Email outro = (Email) objeto;
		if (email == null) {
			if (outro.email != null) {
				return false;
			}
		} else if (!email.equals(outro.email)) {
			return false;
		}
		return true;
	}

	private void assertSintaticalyValid() throws EmailInvalidException {
		if (!FULL_EMAIL.matcher(email).matches()) {
			throw new EmailInvalidException();
		}
	}

	private void assertEmailSizeLessThat120Caracters() throws EmailBiggerThat120Exception {
		if (email.length() > EmailBiggerThat120Exception.MAX) {
			throw new EmailBiggerThat120Exception();
		}
	}

	private void assertNotEmpty() throws EmailEmptyException {
		if (email.isEmpty()) {
			throw new EmailEmptyException();
		}
	}

	private void assertNotNull(String email) throws EmailNullException {
		if (email == null) {
			throw new EmailNullException();
		}
	}

	public String getEmail() {
		return email;
	}

	public String getDomain() {
		return domain;
	}

	public String getUser() {
		return user;
	}
}