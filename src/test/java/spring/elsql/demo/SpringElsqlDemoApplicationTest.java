package spring.elsql.demo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

/**
 * Mocks SpringElsqlDemoApplication main runner test
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
class SpringElsqlDemoApplicationTest {

    @Test
    void contextLoads() {
        try(var mockedSpring = mockStatic(SpringApplication.class)) {
            var args = new String[] { "test" };
            SpringElsqlDemoApplication.main(args);
            mockedSpring.verify(() -> SpringApplication.run(SpringElsqlDemoApplication.class, new String[] { "test" }));
        }
    }
}