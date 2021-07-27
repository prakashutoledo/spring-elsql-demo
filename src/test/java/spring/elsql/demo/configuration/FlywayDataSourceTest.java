package spring.elsql.demo.configuration;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Flyway datasource test annotation
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@Retention(RUNTIME)
@Target(TYPE)
@Import(FlywayTestConfiguration.class)
@interface FlywayDataSourceTest {
}