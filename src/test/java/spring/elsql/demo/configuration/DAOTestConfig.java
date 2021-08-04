package spring.elsql.demo.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import spring.elsql.demo.dao.MessageDAO;

/**
 * DAO test config
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * 
 * @since 1.0
 */
@ComponentScan(basePackageClasses = { MessageDAO.class })
@FlywayDataSourceTest
public class DAOTestConfig {
}