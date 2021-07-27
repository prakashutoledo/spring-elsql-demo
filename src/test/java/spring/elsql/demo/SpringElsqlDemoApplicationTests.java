package spring.elsql.demo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

/**
 * Mocks SpringElsqlDemoApplication main runner test
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
class SpringElsqlDemoApplicationTests {

    @Test
    void contextLoads() {
        mockStatic(SpringApplication.class);
        SpringElsqlDemoApplication.main(new String[] { "test" });
    }
}