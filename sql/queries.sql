DROP SCHEMA IF EXISTS quiz_app;
CREATE SCHEMA quiz_app;


USE quiz_app;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name NVARCHAR(30) NOT NULL,
  last_name  NVARCHAR(30) NOT NULL,
  username  VARCHAR(20)  NOT NULL,
  password   VARCHAR(30)  NOT NULL
);