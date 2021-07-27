package spring.elsql.demo.dao;

import static spring.elsql.demo.SpringElsqlDemoGlobals.*;
import static spring.elsql.demo.dao.mapper.MessageMapper.*;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import spring.elsql.demo.domain.Message;

/**
 * Data access object for CRUD Message operation
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@Repository
public class MessageDAO extends AbstractMySqlDAO {
    /**
     * Creates a new MessageDAO object
     * 
     * @param dataSource a dataSource to set
     */
    @Autowired
    public MessageDAO(DataSource datasource) {
        super(datasource);
    }

    /**
     * Create new Message
     * 
     * @param message a Message to create
     * 
     * @return a newly created Message
     */
    public Message createMessage(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = toParamSource(USER_ID, message.getUserId()).addValue(MESSAGE_DETAILS,
                message.getDetails());
        jdbcTemplate.update(toSqlString("createMessage", params), params, keyHolder);
        message.setId(keyHolder.getKey().longValue());
        return message;
    }

    /**
     * Update messafe based on message id and user id
     * 
     * @param message a message to update is matching message and user id is present
     *                in database
     */
    public void updateMessage(Message message) {
        MapSqlParameterSource params = toParamSource(MESSAGE_ID, message.getId()).addValue(USER_ID, message.getUserId())
                .addValue(MESSAGE_DETAILS, message.getDetails());
        jdbcTemplate.update(toSqlString("updateMessage", params), params);
    }

    /**
     * Find the message matching given message id if present
     * 
     * @param messageId a message id to look for
     * 
     * @return an optional containing matching message
     */
    public Optional<Message> findMessageById(long messageId) {
        return findById("findMessageById", toParamSource(MESSAGE_ID, messageId), MESSAGE_MAPPER);
    }

    /**
     * Delete all message for given userId
     * 
     * @param userId a user id to look to delete all message
     */
    public void deleteMessageByUser(long userId) {
        MapSqlParameterSource params = toParamSource(USER_ID, userId);
        jdbcTemplate.update(toSqlString("deleteMessageByUser", params), params);
    }

    /**
     * Find the list of all messages for given user id
     * 
     * @param userId a matching user id to look for messages
     * 
     * @return a list of found messages for given user id
     */
    public List<Message> getMessageByUser(long userId) {
        MapSqlParameterSource params = toParamSource(USER_ID, userId);
        return jdbcTemplate.query(toSqlString("getMessageByUser", params), params, MESSAGE_MAPPER);
    }
}