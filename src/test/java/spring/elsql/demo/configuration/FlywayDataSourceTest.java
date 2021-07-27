package spring.elsql.demo.configuration;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
public @interface FlywayDataSourceTest {

}
