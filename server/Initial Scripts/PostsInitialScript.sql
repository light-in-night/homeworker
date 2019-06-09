DROP DATABASE IF EXISTS homeworkerdb;
CREATE DATABASE homeworkerdb;
USE homeworkerdb;

DROP TABLE IF EXISTS posts;
-- remove table if it already exists and start from scratch
CREATE TABLE posts (
   id         bigint auto_increment,
   userId_FK  bigint,
   content    VARCHAR(1000) NOT NULL,
   rating     bigint default 0,
   creationtimestamp timestamp default CURRENT_TIMESTAMP,
   foreign key (userId_FK) references users(id),
   primary key (id)
);


