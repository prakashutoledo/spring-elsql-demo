package spring.elsql.demo.dao;

import static com.opengamma.elsql.ElSqlConfig.MYSQL;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.opengamma.elsql.ElSqlBundle;
import com.opengamma.elsql.ElSqlConfig;

import spring.elsql.demo.domain.BaseIdDomain;

/**
 * Abstract base elsql DAO for creating jdbc template
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
public abstract class BaseElSqlDAO {
    private final ElSqlBundle elsqlBundle;
    protected final NamedParameterJdbcTemplate jdbcTemplate;

    public BaseElSqlDAO(DataSource datasource) {
        this(datasource, MYSQL);
    }

    public BaseElSqlDAO(DataSource datasource, ElSqlConfig elsqlConfig) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.elsqlBundle = ElSqlBundle.of(elsqlConfig, this.getClass());
    }

    /**
     * Returns the created jdbc template using datasource and elsql config
     * 
     * @return a jdbc template
     */
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Find the matching fragment name without specific parameters in elsql if
     * present
     * 
     * @param fragmentName a fragment name to search in elsql bundle
     * @return a sql string for matching given fragment name
     */
    public String getSqlString(String fragmentName) {
        return this.elsqlBundle.getSql(fragmentName).trim();
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
    public String getSqlString(String fragmentName, SqlParameterSource paramSource) {
        return this.elsqlBundle.getSql(fragmentName, paramSource).trim();
    }

    /**
     * Creates a new map sql paramater source from given key and value pair
     * 
     * @param key   a key for sql paramater source
     * @param value a value to substitute for given key
     * @return a newly created Spring map sql paramater source
     */
    public MapSqlParameterSource paramSource(String key, Object value) {
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
            return Optional.of(
                    getJdbcTemplate().queryForObject(getSqlString(fragmentName, paramSource), paramSource, rowMapper));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }
}