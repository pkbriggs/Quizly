package users;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UserTest {

	@Test
	public void testSearch1() {
		List<User> results = User.search("test");
		for (User user: results) {
			System.out.println(String.format("%d: %s", user.getID(), user.getUsername()));
		}
	}

}
