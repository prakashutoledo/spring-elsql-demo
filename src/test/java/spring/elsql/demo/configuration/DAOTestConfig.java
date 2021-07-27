package spring.elsql.demo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * DAO test config
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@ComponentScan(value = "spring.elsql.demo.dao")
@PropertySource(value = "classpath:/config/test.properties")
@PropertySource(value = "classpath:/config/test.properties.ignore", ignoreResourceNotFound = true)
@FlywayDataSourceTest
public class DAOTestConfig {
}