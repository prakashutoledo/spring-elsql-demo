package spring.elsql.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Custom test properties configuration
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@Configuration
@PropertySource(value = "classpath:/test.properties")
@PropertySource(value = "classpath:/test.ignore.properties", ignoreResourceNotFound = true)
public class TestPropertiesConfig {
}
