package spring.elsql.demo.dao;

import static spring.elsql.demo.SpringElsqlDemoGlobals.EMAIL;
import static spring.elsql.demo.SpringElsqlDemoGlobals.FIRST_NAME;
import static spring.elsql.demo.SpringElsqlDemoGlobals.LAST_NAME;
import static spring.elsql.demo.SpringElsqlDemoGlobals.MIDDLE_INITIAL;
import static spring.elsql.demo.SpringElsqlDemoGlobals.USER_ID;
import static spring.elsql.demo.dao.mapper.UserMapper.USER_MAPPER;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import spring.elsql.demo.domain.User;
import spring.elsql.demo.domain.UserGetRequest;

/**
 * Data access object for CRUD User operation
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@Repository
public class UserDAO extends BaseElSqlDAO {

	/**
	 * Creates a new UserDAO object
	 * 
	 * @param dataSource a dataSource to set
	 */
	@Autowired
	public UserDAO(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Create new user
	 * 
	 * @param user a user to create
	 * 
	 * @return a newly created user
	 */
	public User createUser(User user) {
		MapSqlParameterSource params = paramSource(FIRST_NAME, user.getFirstName())
				.addValue(LAST_NAME, user.getLastName()).addValue(EMAIL, user.getEmail())
				.addValue(MIDDLE_INITIAL, user.getMiddleInitial());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(this.getSqlString("createUser", params), params, keyHolder);

		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	/**
	 * Update user based on id
	 * 
	 * @param user a user to update is matching id is present in database
	 */
	public void updateUser(User user) {
		MapSqlParameterSource params = paramSource(USER_ID, user.getId()).addValue(FIRST_NAME, user.getFirstName())
				.addValue(LAST_NAME, user.getLastName()).addValue(EMAIL, user.getEmail())
				.addValue(MIDDLE_INITIAL, user.getMiddleInitial());
		getJdbcTemplate().update(getSqlString("updateUser", params), params);
	}

	/**
	 * Get the list of all user matching filters provided
	 * 
	 * @param request a get request filter to filter results
	 * 
	 * @return a list of user matching filters
	 */
	public List<User> getUser(UserGetRequest request) {
		MapSqlParameterSource params = paramSource("firstNames", request.getFirstNames())
				.addValue("lastNames", request.getLastNames()).addValue("emails", request.getEmails());

		return getJdbcTemplate().query(getSqlString("getUser", params), params, USER_MAPPER);
	}

	/**
	 * Find the user matching given user id if present
	 * 
	 * @param id a user id to look for
	 * 
	 * @return a optional containing matching user
	 */
	public Optional<User> findUserById(long id) {
		return findById("findUserById", paramSource(USER_ID, id), USER_MAPPER);
	}
}
