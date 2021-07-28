package spring.elsql.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.elsql.demo.dao.MessageDAO;
import spring.elsql.demo.dao.UserDAO;
import spring.elsql.demo.domain.Message;
import spring.elsql.demo.domain.User;
import spring.elsql.demo.domain.UserGetRequest;

/**
 * Service class for User
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 26, 2021
 * 
 * @since 1.0
 */
@Service
@Transactional
public class UserService {
    private final UserDAO userDAO;
    private final MessageDAO messageDAO;

    @Autowired
    public UserService(UserDAO userDAO, MessageDAO messageDAO) {
        this.userDAO = userDAO;
        this.messageDAO = messageDAO;
    }

    /**
     * First create a new user and creates all the messages within user
     * 
     * @param user a user to create
     */
    public void createUser(final User user) {
        final User newUser = userDAO.createUser(user);
        newUser.getMessages().stream().map(message -> includeUserId(message, user.getId()))
                .forEach(messageDAO::createMessage);
    }

    /**
     * Updates the given user and it's messages
     * 
     * @param user a user to update
     */
    public void updateUser(User user) {
        userDAO.updateUser(user);
        user.getMessages().forEach(messageDAO::updateMessage);
    }

    /**
     * Find matching user based on given userId
     * 
     * @param userId an user id to look for
     * @return a matching user if present otherwise throw illegal argument exception
     */
    public User findUserById(long userId) {
        return userDAO.findUserById(userId).map(this::includeMessage)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with id: %d not found", userId)));
    }

    /**
     * Get the list of user for matching request filters
     * 
     * @param request an user get request filters to filter results for
     * @return a list of user matching the request
     */
    public List<User> getUser(UserGetRequest request) {
        return userDAO.getUser(request).stream().map(this::includeMessage).collect(Collectors.toList());
    }

    /**
     * Delete user by id. If no matching user is found, illegal argument exception
     * is thrown
     * 
     * @param userId an user id to delete for
     */
    public void deleteUserById(long userId) {
        userDAO.findUserById(userId).ifPresentOrElse(this::deleteUserAndMessage, () -> {
            throw new IllegalArgumentException(String.format("Unable to delete user: %d", userId));
        });
    }

    private User includeMessage(User user) {
        user.setMessages(messageDAO.getMessageByUser(user.getId()));
        return user;
    }

    private Message includeUserId(Message message, long userId) {
        message.setUserId(userId);
        return message;
    }

    private void deleteUserAndMessage(User user) {
        userDAO.deleteUserById(user.getId());
        messageDAO.deleteMessageByUser(user.getId());
    }
}
