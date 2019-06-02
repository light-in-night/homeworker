-- SETUP
DROP DATABASE IF EXISTS homeworkerDB;
CREATE DATABASE homeworkerDB;
USE homeworkerDB;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

-- CREATE SEQUENCES
  CREATE

-- CREATE TABLES
CREATE TABLE users (
	userId 		  bigint 		not null auto_increment,
  bitchOf_FK 	bigint		,
	name 		    varchar(20) not null,
	primary 	  key (userId),
  foreign     key (bitchOf_FK) references users(userId)
 );
 
CREATE TABLE posts (
	postId 		  bigint 			not null auto_increment,
  userId_FK 	bigint			not null,
  content 	  varchar(512) 	not null,
  primary 	  key (postId),
  foreign 	  key (userId_FK) references users(userId)
);

-- POPULATE TABLES
INSERT INTO users (bitchOf_FK, name)
VALUES
	(null,'Tornike'),
  (null,'Givi'),
  (null,'Guga');

INSERT INTO posts (userId_FK, content)
VALUES
	  (0,'Tornikes post'),
    (1,'Givis post'),
    (2,'Gugas post');

SELECT posts.postId as post_id,
       posts.content as content,
       users.userId as user_id,
       users.name as user_name
FROM posts
JOIN users
ON posts.userId_FK = users.userId
WHERE posts.userId_FK = 0;
commit;
