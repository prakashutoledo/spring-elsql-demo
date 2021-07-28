# spring-elsql-demo
This project uses Spring Boot, Spring jdbc template with MySQL Elsql to create Data Access Object which includes transaction management and Flyway for database migration.

## To run this project 
* Have a MySQL database up and running 
* Create a new file `application.ignore.properties` in `src/main/resources` with the sample provided in `application.ignore.properties.sample`.  Changes made in this file will be ignored by git while commiting your changes
* `./gradlew bootRun` to actually run the Spring Boot application

## To run test
Since, it uses actual MySQL schema to be created before test using Flyway and cleanup after the test is complete, Thus, we need a MySQL database up and running.
* Create new file `test.ignore.properties` in `src/test/resources` with the sample provided in `test.ignore.properties.sample`. Changes made in this file will be ignored by git while commiting your changes
* `./gradlew test`
