package spring.elsql.demo.dao.mapper;


import static spring.elsql.demo.dao.ElSqlDAOGlobals.*;

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
    /**
     * A singleton user mapper
     */
    public static final UserMapper USER_MAPPER = new UserMapper();
    
    private UserMapper() {
        // private initialization
    }

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setFirstName(resultSet.getString(FIRST_NAME));
        user.setMiddleInitial(resultSet.getString(MIDDLE_INITIAL));
        user.setLastName(resultSet.getString(LAST_NAME));
        user.setEmail(resultSet.getString(EMAIL));
        return user;
    }
}
