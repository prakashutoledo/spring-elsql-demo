package spring.elsql.demo.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.elsql.demo.configuration.DAOTestConfig;
import spring.elsql.demo.domain.Message;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { MessageDAO.class })
@ContextConfiguration(classes = { DAOTestConfig.class })
@Sql({ "classpath:/spring/elsql/demo/dao/CleanUp.sql", "classpath:/spring/elsql/demo/dao/TestMessage.sql" })
class MessageDAOTest {
    @Autowired
    private MessageDAO messageDAO;

    @Test
    void createMessage() {
        Message created = messageDAO.createMessage(message(1L, "This is test message"));
        assertEquals(1L, created.getUserId(), "Message user id");
        assertEquals("This is test message", created.getDetails(), "Message details");
    }

    @Test
    void findMessageById() {
        Message created = messageDAO.createMessage(message(2L, "Message"));

        Optional<Message> found = messageDAO.findMessageById(created.getId());
        assertTrue(found.isPresent(), "Message do exist");

        Message foundMessage = found.get();
        assertEquals(2L, foundMessage.getUserId(), "Message user id");
        assertEquals("Message", foundMessage.getDetails(), "Message details");
    }

    @Test
    void findMessageByIdNotFound() {
        assertTrue(messageDAO.findMessageById(1000L).isEmpty(), "Message does not exist");
    }

    @Test
    void getMessageByUser() {
        List<Message> messages = messageDAO.getMessageByUser(1L);
        assertNotNull(messages, "Messages is not null value");
        assertEquals(3, messages.size(), "Message list size");
        assertThat(messages,
                contains(
                        allOf(hasProperty("id", is(equalTo(1L))), hasProperty("userId", is(equalTo(1L))),
                                hasProperty("details", is(equalTo("Message 1")))),
                        allOf(hasProperty("id", is(equalTo(2L))), hasProperty("userId", is(equalTo(1L))),
                                hasProperty("details", is(equalTo("Message 2")))),
                        allOf(hasProperty("id", is(equalTo(3L))), hasProperty("userId", is(equalTo(1L))),
                                hasProperty("details", is(equalTo("Message 3"))))));

        messages = messageDAO.getMessageByUser(2L);
        assertThat(messages, contains(allOf(hasProperty("id", is(equalTo(4L))), hasProperty("userId", is(equalTo(2L))),
                hasProperty("details", is(equalTo("Message 4"))))));
        assertNotNull(messages, "Messages is not null value");
        assertEquals(1, messages.size(), "Message list size");

        messages = messageDAO.getMessageByUser(3L);
        assertThat(messages, contains(allOf(hasProperty("id", is(equalTo(5L))), hasProperty("userId", is(equalTo(3L))),
                hasProperty("details", is(equalTo("Message 5"))))));
        assertNotNull(messages, "Messages is not null value");
        assertEquals(1, messages.size(), "Message list size");
    }

    @Test
    void getMessageByUserEmptyResult() {
        List<Message> messages = messageDAO.getMessageByUser(1000L);
        assertNotNull(messages, "Messages is not null value");
        assertTrue(messages.isEmpty(), "Message list size");
    }

    @Test
    void updateMessage() {
        Message created = messageDAO.createMessage(message(3L, "This is test message"));
        created.setDetails("This is updated message details");
        messageDAO.updateMessage(created);
        Optional<Message> found = messageDAO.findMessageById(created.getId());

        Message foundMessage = found.get();
        assertEquals(3L, foundMessage.getUserId(), "Message user id");
        assertEquals("This is updated message details", foundMessage.getDetails(), "Message details");
    }

    @Test
    void deleteMessageByUser() {
        // Before delete
        assertTrue(messageDAO.findMessageById(1L).isPresent(), "User 1, message Id: 1 is present");
        assertTrue(messageDAO.findMessageById(2L).isPresent(), "User 1, message Id: 2 is present");
        assertTrue(messageDAO.findMessageById(3L).isPresent(), "User 1, message Id: 3 is present");

        // After delete
        messageDAO.deleteMessageByUser(1L);
        assertTrue(messageDAO.findMessageById(1L).isEmpty(), "User 1, message Id: 1 deleted");
        assertTrue(messageDAO.findMessageById(2L).isEmpty(), "User 1, message Id: 2 deleted");
        assertTrue(messageDAO.findMessageById(3L).isEmpty(), "User 1, message Id: 3 deleted");
    }

    private Message message(long userId, String details) {
        Message message = new Message();
        message.setUserId(userId);
        message.setDetails(details);

        return message;
    }
}
