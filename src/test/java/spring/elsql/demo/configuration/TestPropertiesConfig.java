package spring.elsql.demo.configuration;

import org.springframework.context.annotation.PropertySource;

/**
 * Custom test properties configuration
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@PropertySource(value = "classpath:/test.properties")
@PropertySource(value = "classpath:/test.properties.ignore", ignoreResourceNotFound = true)
public class TestPropertiesConfig {
}
