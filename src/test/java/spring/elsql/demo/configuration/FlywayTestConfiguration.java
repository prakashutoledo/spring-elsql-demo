package spring.elsql.demo.configuration;

import java.util.Arrays;
import java.util.Map;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Flyway test configuration which creates missing flyway, dataSource and
 * namedParamaterJdbcTemplate
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@ConditionalOnMissingBean(value = { Flyway.class, DataSource.class, NamedParameterJdbcTemplate.class })
@Import(TestPropertiesConfig.class)
public class FlywayTestConfiguration {
    @Bean(destroyMethod = "clean")
    public Flyway Flyway(FlywayTestProperties flywayProperties) {
        String flywaySchemaName = String.format("%s_%d", flywayProperties.getSchema(), System.currentTimeMillis());
        Flyway flyway = Flyway.configure()
                .dataSource(flywayProperties.getUrl(), flywayProperties.getUser(), flywayProperties.getPassword())
                .schemas(flywaySchemaName).placeholders(Map.of("schema", flywaySchemaName)).load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public DataSource dataSource(Flyway flyway, FlywayTestProperties flywayProperties) {
        String flywaySchemaName = Arrays.stream(flyway.getConfiguration().getSchemas()).findFirst().orElseThrow();
        return DataSourceBuilder.create().url(String.format("%s/%s", flywayProperties.getUrl(), flywaySchemaName))
                .username(flywayProperties.getUser()).password(flywayProperties.getPassword()).build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplete(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}