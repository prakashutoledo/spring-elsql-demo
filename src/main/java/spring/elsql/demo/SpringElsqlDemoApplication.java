package spring.elsql.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot main application
 * 
 * @author Prakash Khadka
 * @since 1.0
 */
@EnableTransactionManagement
@SpringBootApplication
public class SpringElsqlDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringElsqlDemoApplication.class, args);
    }
}