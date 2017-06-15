DROP DATABASE IF EXISTS quiz_app;
CREATE DATABASE quiz_app
  DEFAULT CHARACTER SET utf8;

USE quiz_app;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id    INT AUTO_INCREMENT,
  first_name VARCHAR(30)  NOT NULL,
  last_name  VARCHAR(30)  NOT NULL,
  username   VARCHAR(20)  NOT NULL UNIQUE,
  password   VARCHAR(200) NOT NULL,

  CONSTRAINT users_pk PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS quizzes;
CREATE TABLE quizzes (
  quiz_id      INT AUTO_INCREMENT,
  author_id    INT          NOT NULL,
  title        VARCHAR(100) NOT NULL,
  date_created DATE         NOT NULL,

  CONSTRAINT quizzes_pk PRIMARY KEY (quiz_id),
  CONSTRAINT quizzes_fk FOREIGN KEY (author_id) REFERENCES users (user_id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  question_id   INT AUTO_INCREMENT,
  type_id       INT NOT NULL,
  question_text VARCHAR(1000),
  media         VARCHAR(1000),

  CONSTRAINT questions_pk PRIMARY KEY (question_id)
);


DROP TABLE IF EXISTS questions_quizzes;
CREATE TABLE questions_quizzes (
  question_id INT,
  quiz_id     INT NOT NULL,
  index_id    INT NOT NULL,

  CONSTRAINT questions_quizzes_pk PRIMARY KEY (question_id, quiz_id),
  CONSTRAINT questions_quizzes_fk1 FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id),
  CONSTRAINT questions_quizzes_fk2 FOREIGN KEY (question_id) REFERENCES questions (question_id)
);


DROP TABLE IF EXISTS answers;
CREATE TABLE answers (
  answer_id    INT NOT NULL AUTO_INCREMENT,
  type_id      INT NOT NULL,
  answer_text  VARCHAR(500),
  answer_text2 VARCHAR(500),
  is_correct   BOOL,
  media        VARCHAR(1000),
  media2       VARCHAR(1000),
  index_id     INT,

  CONSTRAINT answers_pk PRIMARY KEY (answer_id)
);

DROP TABLE IF EXISTS answers_questions;
CREATE TABLE answers_questions (
  answer_id   INT NOT NULL,
  question_id INT NOT NULL,

  CONSTRAINT answers_questions_pk PRIMARY KEY (answer_id, question_id),
  CONSTRAINT answers_questions_fk1 FOREIGN KEY (answer_id) REFERENCES answers (answer_id),
  CONSTRAINT answers_questions_fk2 FOREIGN KEY (question_id) REFERENCES questions (question_id)

);

DROP TABLE IF EXISTS users_quiz_history;
CREATE TABLE users_quiz_history (
  user_id  INT NOT NULL,
  quiz_id  INT NOT NULL,
  status   INT NOT NULL,
  duration DOUBLE,
  score    DOUBLE,

  CONSTRAINT users_quiz_history_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT users_quiz_history_fk1 FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);









