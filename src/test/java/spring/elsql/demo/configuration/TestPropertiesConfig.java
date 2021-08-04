package spring.elsql.demo.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@EnableConfigurationProperties(FlywayTestProperties.class)
@PropertySource(value = "classpath:/test.properties")
@PropertySource(value = "classpath:/test.ignore.properties", ignoreResourceNotFound = true)
class TestPropertiesConfig {
    @Bean
    FlywayTestProperties flywayProperties() {
        return new FlywayTestProperties();
    }
}
