DROP DATABASE IF EXISTS quiz_app;
CREATE DATABASE quiz_app
  DEFAULT CHARACTER SET utf8;

USE quiz_app;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id       INT AUTO_INCREMENT,
  first_name    NVARCHAR(30)  NOT NULL,
  last_name     NVARCHAR(30)  NOT NULL,
  username      NVARCHAR(20)  NOT NULL UNIQUE,
  password      NVARCHAR(200) NOT NULL,
  gender        NVARCHAR(20),
  country       NVARCHAR(50),
  picture       NVARCHAR(1000),
  date_of_birth DATE,
  status        INT           DEFAULT 0,

  CONSTRAINT users_pk PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS announcements;
CREATE TABLE announcements (
  id            INT AUTO_INCREMENT,
  text          NVARCHAR(2000),
  user_id       INT,

  CONSTRAINT announcements_fk PRIMARY KEY (id),
  CONSTRAINT announcements_fk FOREIGN KEY (user_id) REFERENCES users(user_id)
);

DROP TABLE IF EXISTS quizzes;
CREATE TABLE quizzes (
  quiz_id      INT AUTO_INCREMENT,
  author_id    INT           NOT NULL,
  title        NVARCHAR(100) NOT NULL,
  date_created DATE          NOT NULL,

  CONSTRAINT quizzes_pk PRIMARY KEY (quiz_id),
  CONSTRAINT quizzes_fk FOREIGN KEY (author_id) REFERENCES users (user_id)
);

DROP TABLE IF EXISTS questions;
/*    Q   */
CREATE TABLE questions (
  question_id   INT AUTO_INCREMENT,
  question_text NVARCHAR(1000),
  blank_text    NVARCHAR(1000),
  media         NVARCHAR(1000),

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
  answer_text  NVARCHAR(500),
  answer_text2 NVARCHAR(500),
  is_correct   BOOL,
  is_text      BOOL         DEFAULT TRUE,

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
  duration NVARCHAR(100),
  score    DOUBLE,

  CONSTRAINT users_quiz_history_fk FOREIGN KEY (user_id) REFERENCES users (user_id),
  CONSTRAINT users_quiz_history_fk1 FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id)
);


DROP TABLE IF EXISTS friends;
CREATE TABLE friends (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  friend_one INT NOT NULL, # A friend
  friend_two INT NOT NULL, # B friend
  status     INT             DEFAULT 0, # A->B friendship status   (no duplications B->A row)
  FOREIGN KEY (friend_one) REFERENCES users (user_id),
  FOREIGN KEY (friend_two) REFERENCES users (user_id)
);
# if A sends friend request to B the following row would be like this:
# A B 0
# when B confirms the request status changes to 1

DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  friend_one INT  NOT NULL,
  friend_two INT  NOT NULL,
  message    NVARCHAR(200),
  status     INT             DEFAULT 0,
  FOREIGN KEY (friend_one) REFERENCES users (user_id),
  FOREIGN KEY (friend_two) REFERENCES users (user_id)
);





