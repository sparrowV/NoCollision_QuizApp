DROP DATABASE IF EXISTS quiz_app;
CREATE DATABASE quiz_app
DEFAULT CHARACTER SET utf8;

USE quiz_app;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id   INT AUTO_INCREMENT,
  first_name NVARCHAR(30) NOT NULL,
  last_name  NVARCHAR(30) NOT NULL,
  username  VARCHAR(20)  NOT NULL,
  password   VARCHAR(200)  NOT NULL,

  CONSTRAINT users_pk PRIMARY KEY(user_id)
);

DROP TABLE IF EXISTS quizzes;
CREATE TABLE quizzes (
  quiz_id   INT AUTO_INCREMENT,
  author_id INT NOT NULL,
  title  VARCHAR(100) NOT NULL,
  date_created  DATE  NOT NULL,

  CONSTRAINT quizzes_pk PRIMARY KEY(quiz_id),
  CONSTRAINT quizzes_fk FOREIGN KEY(author_id) REFERENCES users(user_id)
);



DROP TABLE IF EXISTS questions_to_quizzes;
CREATE TABLE questions_to_quizzes (
  question_id   INT AUTO_INCREMENT,
  type_id INT NOT NULL,
  quiz_id  INT NOT NULL,

  CONSTRAINT questions_to_quizzes_pk PRIMARY KEY(question_id, type_id),
  CONSTRAINT questions_to_quizzes_fk FOREIGN KEY(quiz_id) REFERENCES quizzes(quiz_id)
);


DROP TABLE IF EXISTS questions_plain;
CREATE TABLE questions_plain (
  question_id   INT AUTO_INCREMENT,
  type_id INT NOT NULL,
  question  VARCHAR(1000) NOT NULL,

  CONSTRAINT questions_plain_pk PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS answers_plain;
CREATE TABLE answers_plain (
  answer_id   INT AUTO_INCREMENT,
  answer  VARCHAR(500) NOT NULL,

  CONSTRAINT answers_plain_pk PRIMARY KEY(answer_id)
);

DROP TABLE IF EXISTS answers_to_questions_plain;
CREATE TABLE answers_to_questions_plain (
  answer_id   INT NOT NULL,
  question_id  INT NOT NULL,

  CONSTRAINT answers_to_questions_plain_pk PRIMARY KEY(answer_id, question_id),
  CONSTRAINT answers_to_questions_plain_fk1 FOREIGN KEY(answer_id) REFERENCES answers_plain(answer_id),
  CONSTRAINT answers_to_questions_plain_fk2 FOREIGN KEY(question_id) REFERENCES questions_plain(question_id)

);


DROP TABLE IF EXISTS questions_multchoice;
CREATE TABLE questions_multchoice (
  question_id   INT AUTO_INCREMENT,
  type_id INT NOT NULL,
  choice1  VARCHAR(200) NOT NULL,
  choice2  VARCHAR(200) NOT NULL,
  choice3  VARCHAR(200) NOT NULL,
  choice4  VARCHAR(200) NOT NULL,
  choice5  VARCHAR(200) NOT NULL,
  answer	INT NOT NULL,

  CONSTRAINT questions_multchoice_pk PRIMARY KEY(question_id)
);