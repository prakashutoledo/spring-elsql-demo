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
    /**
     * Main entry point of SpringElsqlDemoApplication
     * @param args a command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringElsqlDemoApplication.class, args);
    }
}