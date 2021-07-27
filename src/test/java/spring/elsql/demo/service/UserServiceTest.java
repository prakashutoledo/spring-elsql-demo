/**
 * 
 */
package spring.elsql.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import spring.elsql.demo.dao.MessageDAO;
import spring.elsql.demo.dao.UserDAO;
import spring.elsql.demo.domain.Message;
import spring.elsql.demo.domain.User;

/**
 * Service test for User
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 26, 2021
 * 
 * @since 1.0
 */
class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @Mock
    private MessageDAO messageDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void initMocks() {
        openMocks(this);
    }

    @Test
    void createUser() {
        User user = new User();
        user.setMessages(List.of(new Message(), new Message()));
        when(userDAO.createUser(any())).thenReturn(user);
        when(messageDAO.createMessage(any())).thenReturn(new Message());

        userService.createUser(user);

        verify(userDAO).createUser(eq(user));
        verify(messageDAO, times(2)).createMessage(any());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setMessages(List.of(new Message(), new Message(), new Message()));

        doNothing().when(userDAO).updateUser(eq(user));
        doNothing().when(messageDAO).updateMessage(any());

        userService.updateUser(user);

        verify(userDAO).updateUser(eq(user));
        verify(messageDAO, times(3)).updateMessage(any());
    }

    @Test
    void findUserById() {
        var user = new User();
        user.setId(1L);
        user.setMessages(List.of(new Message()));

        when(userDAO.findUserById(anyLong())).thenReturn(Optional.of(user));
        when(messageDAO.getMessageByUser(eq(1L))).thenReturn(user.getMessages());

        assertEquals(user, userService.findUserById(1L), "Found user should match original");
        verify(userDAO).findUserById(eq(1L));
        verify(messageDAO).getMessageByUser(eq(1L));
    }

    @Test
    void findUserByIdNotFound() {
        when(userDAO.findUserById(anyLong())).thenReturn(Optional.empty());
        var exception = assertThrows(IllegalArgumentException.class, () -> userService.findUserById(1L),
                "Throws Exception");
        assertNotNull(exception, "Exception should not be null");
        assertEquals("User with id: 1 not found", exception.getMessage(), "Exception message");
    }

    @Test
    void deleteUserById() {
        var user = new User();
        user.setId(2L);
        user.setMessages(List.of(new Message(), new Message()));

        when(userDAO.findUserById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUserById(2L);

        verify(userDAO).deleteUserById(eq(2L));
        verify(messageDAO).deleteMessageByUser(eq(2L));
    }

    @Test
    void deleteUserByIdNotFound() {
        when(userDAO.findUserById(anyLong())).thenReturn(Optional.empty());
        var exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(3L),
                "Throws Exception");
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Unable to delete user: 3", exception.getMessage(), "Exception message");
    }
}
