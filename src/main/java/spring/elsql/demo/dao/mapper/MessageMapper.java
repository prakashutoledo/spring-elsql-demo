package spring.elsql.demo.dao.mapper;

import static spring.elsql.demo.SpringElsqlDemoGlobals.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.dao.demo.domain.Message;

/**
 * Result row mapper for {@link Message} 
 * 
 * @author Prakash Khadka
 *         Created On: July 25, 2021
 *         
 * @since 1.0
 */
public class MessageMapper implements RowMapper<Message> {
	public static final MessageMapper MESSAGE_MAPPER =  new MessageMapper();
	
	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message message = new Message();
		message.setId(rs.getLong(MESSAGE_ID));
		message.setUserId(rs.getLong(USER_ID));
		message.setDetails(rs.getString(MESSAGE_DETAILS));
		return message;
	}
}
