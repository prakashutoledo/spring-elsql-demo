package spring.elsql.demo.dao.mapper;

import static spring.elsql.demo.SpringElsqlDemoGlobals.EMAIL;
import static spring.elsql.demo.SpringElsqlDemoGlobals.FIRST_NAME;
import static spring.elsql.demo.SpringElsqlDemoGlobals.LAST_NAME;
import static spring.elsql.demo.SpringElsqlDemoGlobals.MIDDLE_INITIAL;
import static spring.elsql.demo.SpringElsqlDemoGlobals.USER_ID;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.elsql.demo.domain.User;

/**
 * Result row mapper for {@link User}
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
public class UserMapper implements RowMapper<User> {
    public static final UserMapper USER_MAPPER = new UserMapper();

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(USER_ID));
        user.setFirstName(rs.getString(FIRST_NAME));
        user.setMiddleInitial(rs.getString(MIDDLE_INITIAL));
        user.setLastName(rs.getString(LAST_NAME));
        user.setEmail(rs.getString(EMAIL));
        return user;
    }
}