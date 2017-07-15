USE quiz_app;

INSERT INTO users (first_name, last_name, username, password, gender, country, picture, date_of_birth, status)
	VALUE ('Dude', 'Lebowski', 'asd', '688787d8ff144c502c7f5cffaafe2cc588d86079f9de88304c26b0cb99ce91c6',
	       'male', 'Los Angeles', 'https://www.parent.co/wp-content/uploads/2016/04/The-Big-Lebowski-White-Russian.jpeg',
	       '1949-12-04', 1);


INSERT INTO users (first_name, last_name, username, password)
VALUES ('Tornike', 'Gurgenidze', 'Tokoko', 'pass1'),
	('Misho', 'Zhgenti', 'Mishiko', 'pass2'),
	('Otar', 'Jankhoteli', 'Jankho', 'pass3'),
	('Otiko', 'Dvalishvili', 'Dvali', 'pass4'),
	('Irakli', 'Chkuaseli', 'Chkua', 'pass5');


INSERT INTO quizzes (author_id, title, date_created, randomized_order, multiple_pages)
VALUES (2, 'History', '2017-06-11', FALSE, FALSE),
	(2, 'Geography', '2017-06-11', FALSE, FALSE),
	(4, 'Math', '2017-06-11', FALSE, FALSE),
	(4, 'CS', '2017-06-11', FALSE, FALSE),
	(5, 'Economics', '2017-06-11', FALSE, FALSE);


INSERT INTO questions (question_text)
VALUES ('When did Napoleon invade Russia?'),
	('Which city was destroyed by a great fire in 1666'),
	('What is the highest point in North America?'),
	('Where was the first metro system implemented?'),
	('Who invented the Difference Engine?'),
	('What strait connects Europe with Africa?'),
	('What government policy dictates spending and taxes?');

INSERT INTO questions (question_text, blank_text)
VALUES ('Insert name', '???? Arnold was an American hero in the revolutinary war before defecting to the British');

INSERT INTO questions (question_text)
VALUES ('Match famous walls to their location');


INSERT INTO questions_quizzes (question_id, quiz_id, index_id)
VALUES (1, 1, 1),
	(2, 1, 2),
	(3, 2, 1),
	(4, 1, 3),
	(5, 4, 1),
	(5, 1, 4),
	(6, 2, 2),
	(7, 5, 1),
	(8, 1, 5),
	(9, 1, 6);

INSERT INTO answers (type_id, answer_text)
VALUES (1, '1812'),
	(1, 'London'),
	(1, 'Denali'),
	(1, 'McKinley'),
	(1, 'London'),
	(1, 'UK'),
	(1, 'United Kingdom'),
	(1, 'Great Briatain'),
	(1, 'England');


INSERT INTO answers (type_id, answer_text, is_correct)
VALUES (2, 'Ada Lovelace', FALSE),
	(2, 'Charles Bubbage', TRUE),
	(2, 'Thomas Edison', FALSE),
	(2, 'Alan Turing', FALSE),
	(2, 'Gibraltar', TRUE),
	(2, 'Dardanelles', FALSE),
	(2, 'Monetary Policy', FALSE),
	(2, 'Budget Policy', FALSE),
	(2, 'Fiscal Policy', TRUE);


INSERT INTO answers (type_id, answer_text)
VALUES (1, 'Benedict');


/*
INSERT INTO answers (type_id, answer_text, index_id)
VALUES (3, 'Benedict', 1),
(3 , 'American', 2),
(3 , 'US', 2),
(3 , 'British', 3);
*/

INSERT INTO answers (type_id, answer_text, answer_text2)
VALUES (3, 'Kelasuri', 'Georgia'),
	(3, 'Mexico Wall', 'Trumpland'),
	(3, 'The Wall', 'Westeros'),
	(3, 'Hadrian\'s Wall', 'UK');

INSERT INTO answers_questions (answer_id, question_id)
VALUES (1, 1),
	(2, 2),
	(3, 3),
	(4, 3),
	(5, 4),
	(6, 4),
	(7, 4),
	(8, 4),
	(9, 4),
	(10, 5),
	(11, 5),
	(12, 5),
	(13, 5),
	(14, 6),
	(15, 6),
	(16, 7),
	(17, 7),
	(18, 7),
	(19, 8),
	(20, 9),
	(21, 9),
	(22, 9),
	(23, 9);