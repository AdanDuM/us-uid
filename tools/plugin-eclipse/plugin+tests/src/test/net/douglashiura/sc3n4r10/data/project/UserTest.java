package test.net.douglashiura.sc3n4r10.data.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.douglashiura.leb.uid.scenario.data.User;
import net.douglashiura.leb.uid.scenario.data.primitive.Email;
import net.douglashiura.leb.uid.scenario.data.primitive.SimpleName;

public class UserTest {

	@Test
	public void douglas() throws Exception {
		Email email = new Email("douglashiura@gmail.com");
		SimpleName username = new SimpleName("doug");
		User douglas = new User(email, username, "p4ss");
		assertEquals(email, douglas.getEmail());
		assertEquals(username, douglas.getUsername());
		assertEquals("p4ss", douglas.getPassword());
		assertEquals("user=doug\nemail=douglashiura@gmail.com\npassword=p4ss\n", douglas.toString());
	}

}
