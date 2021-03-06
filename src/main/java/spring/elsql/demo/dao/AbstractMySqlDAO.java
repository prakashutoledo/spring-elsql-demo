package spring.elsql.demo.dao;

import static com.opengamma.elsql.ElSqlConfig.*;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.opengamma.elsql.ElSqlBundle;

import spring.elsql.demo.domain.BaseIdDomain;

/**
 * Abstract elsql DAO for creating jdbc template
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
public abstract class AbstractMySqlDAO {
    private final ElSqlBundle elsqlBundle;
    protected final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Constructor for creating a elsql and jdbcTemplate
     * @param datasource a dataSource to set for jdbcTemplate
     */
    protected AbstractMySqlDAO(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.elsqlBundle = ElSqlBundle.of(MYSQL, getClass());
    }

    /**
     * Find the matching fragment name with given specific parameters to substitute
     * in elsql bundle if present
     * 
     * @param fragmentName a fragment name to search in elsql bundle
     * @param paramSource  a spring sql parameter source
     * 
     * @return a matched sql string from given fragment name
     */
    public String toSqlString(String fragmentName, SqlParameterSource paramSource) {
        return elsqlBundle.getSql(fragmentName, paramSource);
    }

    /**
     * Creates a new map sql parameter source from given key and value pair
     * 
     * @param key   a key for sql parameter source
     * @param value a value to substitute for given key
     * @return a newly created Spring map sql parameter source
     */
    public MapSqlParameterSource toParamSource(String key, Object value) {
        return new MapSqlParameterSource(key, value);
    }

    /**
     * Find domain by id
     * 
     * @param <T>          a domain type
     * @param fragmentName a sql fragment name to search
     * @param paramSource  a sql parameter to substitute in elsql bundle
     * @param rowMapper    a result row mapper of given domain type
     * 
     * @return an optional of type T
     */
    protected <T extends BaseIdDomain> Optional<T> findById(String fragmentName, SqlParameterSource paramSource,
            RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(toSqlString(fragmentName, paramSource), paramSource, rowMapper));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }
}