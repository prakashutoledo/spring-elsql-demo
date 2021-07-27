package spring.elsql.demo.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.elsql.demo.configuration.DAOTestConfig;
import spring.elsql.demo.domain.User;
import spring.elsql.demo.domain.UserGetRequest;

/**
 * Message DAO test
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 *
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserDAO.class })
@ContextConfiguration(classes = { DAOTestConfig.class })
@Sql({ "classpath:/spring/elsql/demo/dao/CleanUp.sql", "classpath:/spring/elsql/demo/dao/TestUser.sql" })
class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    void creatUser() {
        var user = user("Test", "K", "User", "test@user.com");
        User created = userDAO.createUser(user);

        assertEquals(user.getFirstName(), created.getFirstName(), "User first name");
        assertEquals(user.getMiddleInitial(), created.getMiddleInitial(), "User middle initial");
        assertEquals(user.getLastName(), created.getLastName(), "User last name");
        assertEquals(user.getEmail(), created.getEmail(), "User email address");
    }

    @Test
    void findUser() {
        User user = userDAO.createUser(user("Second", null, "User", "second@user.com"));
        Optional<User> found = userDAO.findUserById(user.getId());
        assertTrue(found.isPresent(), "User do exists");
        User actual = found.get();
        assertEquals("Second", actual.getFirstName(), "User first name");
        assertNull(actual.getMiddleInitial(), "User middle initial");
        assertEquals("User", actual.getLastName(), "User last name");
        assertEquals("second@user.com", actual.getEmail(), "User email address");
    }

    @Test
    void findUserNotFound() {
        assertTrue(userDAO.findUserById(10000).isEmpty(), "User with id:1000 doesnt exist");
    }

    @Test
    void getUserWithoutFilters() {
        List<User> users = userDAO.getUser(null);
        assertNotNull(users, "Users is not null value");
        assertEquals(3, users.size(), "User list size");
        assertThat(users, contains(allOf(hasProperty("id", is(equalTo(1L))),
                hasProperty("firstName", is(equalTo("User"))), hasProperty("lastName", is(equalTo("One"))),
                hasProperty("middleInitial", is(nullValue())), hasProperty("email", is(equalTo("user@one.com")))),
                allOf(hasProperty("id", is(equalTo(2L))), hasProperty("firstName", is(equalTo("Test"))),
                        hasProperty("lastName", is(equalTo("Two"))), hasProperty("middleInitial", is(equalTo("A"))),
                        hasProperty("email", is(equalTo("test@two.com")))),
                allOf(hasProperty("id", is(equalTo(10L))), hasProperty("firstName", is(equalTo("Self"))),
                        hasProperty("lastName", is(equalTo("Three"))), hasProperty("middleInitial", is(equalTo("S"))),
                        hasProperty("email", is(equalTo("self@three.com"))))));
    }

    @Test

    void getUserWithFilters() {
        var request = new UserGetRequest();
        request.setUserIds(Set.of(1L, 3L, 4L, 10L));
        request.setFirstNames(Set.of("User", "Self"));
        request.setEmails(Set.of("user@one.com", "self@three.com"));

        List<User> users = userDAO.getUser(request);
        assertNotNull(users, "User is not null value");
        assertEquals(2, users.size(), "User list size");
        assertThat(users, contains(allOf(hasProperty("id", is(equalTo(1L))),
                hasProperty("firstName", is(equalTo("User"))), hasProperty("lastName", is(equalTo("One"))),
                hasProperty("middleInitial", is(nullValue())), hasProperty("email", is(equalTo("user@one.com")))),
                allOf(hasProperty("id", is(equalTo(10L))), hasProperty("firstName", is(equalTo("Self"))),
                        hasProperty("lastName", is(equalTo("Three"))), hasProperty("middleInitial", is(equalTo("S"))),
                        hasProperty("email", is(equalTo("self@three.com"))))));
    }

    @Test
    void updateUser() {
        User user = userDAO.createUser(user("Third", "T", "User", "Third@user.com"));
        userDAO.createUser(user);

        user.setFirstName("Updated");
        user.setMiddleInitial(null);
        user.setLastName("Last");
        user.setEmail("updateduser@email.com");
        userDAO.updateUser(user);

        Optional<User> found = userDAO.findUserById(user.getId());
        assertTrue(found.isPresent(), "User do exists");
        User actual = found.get();
        assertEquals("Updated", actual.getFirstName(), "User first name");
        assertNull(actual.getMiddleInitial(), "User middle initial");
        assertEquals("Last", actual.getLastName(), "User last name");
        assertEquals("updateduser@email.com", actual.getEmail(), "User email address");
    }

    @Test
    void deleteUserById() {
        // Before delete
        assertTrue(userDAO.findUserById(1L).isPresent(), "User with id:1 is present");

        userDAO.deleteUserById(1L);
        // After delete
        assertTrue(userDAO.findUserById(1L).isEmpty(), "User with id:1 doesnt exist");
    }

    private User user(String firstName, String middleInitial, String lastName, String email) {
        var user = new User();
        user.setFirstName(firstName);
        user.setMiddleInitial(middleInitial);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}