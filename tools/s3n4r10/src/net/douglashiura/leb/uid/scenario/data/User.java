package net.douglashiura.leb.uid.scenario.data;

import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;

public class User {

	private Email email;
	private SimpleName username;
	private String password;

	public User(Email email, SimpleName username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Email getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public SimpleName getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return String.format("user=%s\nemail=%s\npassword=%s\n", username.getName(), email.getEmail(), password);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public void setUser(SimpleName username) {
		this.username = username;
	}

}
