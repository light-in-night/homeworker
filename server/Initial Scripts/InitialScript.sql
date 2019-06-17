DROP DATABASE IF EXISTS homeworker;
CREATE DATABASE homeworker;
USE homeworker;

#DELETE TABLES
DROP TABLE IF EXISTS postLike;
DROP TABLE IF EXISTS postCategory;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

#CREATE TABLES
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(100) default "unspecified",
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    karma bigint default 0,
    constraint pkId primary key (id)
);

CREATE TABLE posts (
   id         bigint auto_increment,
   userId  	  bigint,
   content    VARCHAR(1024) NOT NULL,
   rating     bigint default 0,
   creationtimestamp timestamp default CURRENT_TIMESTAMP,
   category  varchar(64) default '',
   foreign key (userId) references users(id),
   primary key (id)
);

CREATE TABLE postLike(
    id BIGINT AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    postId BIGINT NOT NULL,
    liked BOOLEAN NOT NULL,
    CONSTRAINT pkId primary key (id),
	foreign key (userId) references users(id),
	foreign key (postId) references posts(id)
);

CREATE TABLE categories (
	id BIGINT auto_increment,
    name varchar(64) not null,
    description varchar(256) default 'no description',
    constraint pkId primary key (id)
);

CREATE TABLE postCategory(
	id BIGINT auto_increment,
    postId bigint,
    categoryId bigint,
    constraint pkId primary key (id),
    constraint fkPostId foreign key (postId) references posts(id),
    constraint fkCategoryId foreign key (categoryId) references categories(id)
);


###POPULATE DB###

#USERS
INSERT INTO homeworker.users
(first_name, last_name , gender , email , password , karma )
VALUES
('tornike','onoprishvili','male','tonop15@freeuni.edu.ge','something',10),
('guga','gugashvili','male','ggush@freeuni.edu.ge','somethingother',-11),
('givi','givishvili','male','ggivi@freeuni.edu.ge','somethingweird',41);

#POSTS
INSERT INTO  homeworker.posts
(userId , content , rating )
VALUES
(1, 'hello world!',0),
(2, 'fluggengeggenholen!',0),
(3, 'ashmalaxa!',0);

#POST LIKES
INSERT INTO homeworker.postlike
(userId,postId,liked)
VALUES
(1,1,true),(1,2,true),(2,1,true);

#CATEGORY
INSERT INTO homeworker.categories
(name,description)
VALUES
('Jobs', 'Hire workforce or apply for a job!'),
('Life and Style', 'Daily posts from other homeworkers!'),
('Updates', 'System updates and important notifications.');

#POST CATEGORY
INSERT INTO homeworker.postcategory
(postId,categoryId)
VALUES
(1,1),
(1,2),
(2,3),
(3,3);


###SELECTS###

#POSTS

#USERS

#POST LIKES
SELECT postcategory.id, postcategory.postId, postcategory.categoryId
FROM homeworker.postcategory
WHERE postcategory.postId = 1;


INSERT INTO homeworker.postcategory
(postId, categoryId)
VALUES
(3,1);

DELETE FROM homeworker.postcategory
WHERE postcategory.id = 1;


#CATEGORIES
SELECT categories.id, categories.name, categories.description
FROM homeworker.categories
WHERE categories.id = 1;

DELETE FROM homeworker.categories
WHERE categories.id = 1;

#POST CATEGORIES
SELECT COUNT(post.id)
FROM homeworker.postcategory as postcategory
JOIN homeworker.categories as category ON
	  postcategory.categoryId = category.id
JOIN homeworker.posts as post ON
    postcategory.postId = post.id
WHERE category.id = 3;

