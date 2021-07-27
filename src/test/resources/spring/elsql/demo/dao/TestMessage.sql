INSERT INTO elsql_user (user_id, first_name, middle_initial, last_name, email) 
VALUES (1, '1', NULL, 'One', '1@one.com'),
       (2, '2', 'K', 'Two', '2@two.com'),
       (3, '3', 'L', 'Three', '3@three.com');
       
INSERT INTO user_message (message_id, user_id, message_details)
VALUES (1, 1, 'Message 1'),
       (2, 1, 'Message 2'),
       (3, 1, 'Message 3'),
       (4, 2, 'Message 4'),
       (5, 3, 'Message 5');