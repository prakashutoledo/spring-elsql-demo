/**
 * V1.0.0.1__Add_New_Tables.sql
 * Purpose : Creates new table
 * Author  : Prakash Khadka
 * Date    : 07/25/2021
 * Since   : 1.0.1 
 */
 
/***************************************Script Start******************************************/
-- Elsql user table
CREATE table elsql_user(
	user_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(40) NOT NULL,
    middle_initial VARCHAR(1) DEFAULT NULL,
    last_name VARCHAR(40) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id)
)  ENGINE=INNODB;

-- user_message table
CREATE TABLE user_message(
	message_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    message_details VARCHAR(100) NOT NULL,
    PRIMARY KEY (message_id)
) ENGINE=INNODB;

ALTER TABLE user_message
	ADD CONSTRAINT user_message_FK1  
	FOREIGN KEY (user_id) REFERENCES elsql_user(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

/*****************************************Script End*********************************************************/