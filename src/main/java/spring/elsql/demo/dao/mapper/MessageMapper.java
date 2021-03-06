package spring.elsql.demo.dao.mapper;

import static spring.elsql.demo.dao.ElSqlDAOGlobals.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.elsql.demo.domain.Message;

/**
 * Result row mapper for {@link Message}
 * 
 * @author Prakash Khadka Created On: July 25, 2021
 * 
 * @since 1.0
 */
public class MessageMapper implements RowMapper<Message> {
    /**
     * A singleton message mapper
     */
    public static final MessageMapper MESSAGE_MAPPER = new MessageMapper();
    
    private MessageMapper() {
        // private initialization
    }

    @Override
    public Message mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var message = new Message();
        message.setId(resultSet.getLong(MESSAGE_ID));
        message.setUserId(resultSet.getLong(USER_ID));
        message.setDetails(resultSet.getString(MESSAGE_DETAILS));
        return message;
    }
}
