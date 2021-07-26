package spring.elsql.demo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.dao.demo.domain.User;
import spring.elsql.demo.configuration.DAOTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DAOTestConfig.class})
public class UserDAOTest {
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void creatUser() {
		User user = user("Test", "K", "User", "test@user.com");
		User created = userDAO.createUser(user);
		
		assertEquals(user.getFirstName(), created.getFirstName(), "User first name");
		assertEquals(user.getMiddleInitial(), created.getMiddleInitial(), "User middle initial");
		assertEquals(user.getLastName(), created.getLastName(), "User last name");
		assertEquals(user.getEmail(), created.getEmail(), "User email address");
	}
	
	@Test 
	public void findUser() {
		User user = userDAO.createUser(user("Second", null, "User", "second@user.com"));
		Optional<User> found =  userDAO.findUserById(user.getId());
		assertTrue(found.isPresent(), "User do exists");
		User actual = found.get();
		assertEquals("Second", actual.getFirstName(), "User first name");
		assertNull(actual.getMiddleInitial(), "User middle initial");
		assertEquals("User", actual.getLastName(), "User last name");
		assertEquals("second@user.com", actual.getEmail(), "User email address");
	}
	
	@Test
	public void updateUser() {
		User user = userDAO.createUser(user("Third", "T", "User", "Third@user.com"));
		userDAO.createUser(user);
		
		user.setFirstName("Updated");
		user.setMiddleInitial(null);
		user.setLastName("Last");
		user.setEmail("updateduser@email.com");
		userDAO.updateUser(user);
		
		Optional<User> found =  userDAO.findUserById(user.getId());
		assertTrue(found.isPresent(), "User do exists");
		User actual = found.get();
		assertEquals("Updated", actual.getFirstName(), "User first name");
		assertNull(actual.getMiddleInitial(), "User middle initial");
		assertEquals("Last", actual.getLastName(), "User last name");
		assertEquals("updateduser@email.com", actual.getEmail(), "User email address");
	}
	
	private User user(String firstName, String middleInitial, String lastName, String email) {
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleInitial(middleInitial);
		user.setLastName(lastName);
		user.setEmail(email);
		return user;
	}
}
