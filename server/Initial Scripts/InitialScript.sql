DROP DATABASE IF EXISTS homeworker;
CREATE DATABASE homeworker;
USE homeworker;

#DELETE TABLES
DROP TABLE IF EXISTS postLike, postCategory, categories, posts, users, messages;

#CREATE TABLES
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(100) default 'unspecified',
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    karma bigint default 0,
    constraint pkId primary key (id),
	unique key (email)
);

CREATE TABLE posts (
   id         BIGINT AUTO_INCREMENT,
   userId  	  BIGINT,
   contents    VARCHAR(1024) NOT NULL,
   creationTimestamp timestamp default CURRENT_TIMESTAMP,
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
CREATE TABLE comment(
    id BIGINT AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    postId BIGINT NOT NULL,
    contents    VARCHAR(1024) NOT NULL,
    CONSTRAINT pkId primary key (id),
	foreign key (userId) references users(id),
	foreign key (postId) references posts(id)
);

CREATE TABLE categories (
	id BIGINT AUTO_INCREMENT,
    name varchar(64) not null,
    description varchar(256) default 'no description',
    constraint pkId primary key (id)
);

CREATE TABLE postCategory(
	id BIGINT AUTO_INCREMENT,
    postId BIGINT,
    categoryId BIGINT,
    constraint pkId primary key (id),
    constraint fkPostId foreign key (postId) references posts(id),
    constraint fkCategoryId foreign key (categoryId) references categories(id)
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT,
    senderId BIGINT,
    receiverId BIGINT,
    message VARCHAR(2000),
    sendTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pkId PRIMARY KEY (id),
    CONSTRAINT fkSenderId FOREIGN KEY (senderId) REFERENCES users(id),
    CONSTRAINT fkReceiverId FOREIGN KEY (receiverId) REFERENCES users(id)
);


#####################################POPULATE DB####################################

#USERS
INSERT INTO homeworker.users
(first_name, last_name , gender , email , password , karma )
VALUES
('tornike','onoprishvili','male','tonop15@freeuni.edu.ge','something',10),
('guga','gugashvili','male','ggush@freeuni.edu.ge','somethingother',-11),
('givi','givishvili','male','ggivi@freeuni.edu.ge','somethingweirddqw',41),
('magda','zandra','female','mlac@freeuni.edu.ge','dqwe',5),
('marco','lianora','male','ddasd@mit.edu.us','aaqqee',-31),
('pope','gvalia','male','ggivi@stanford.edu','blah',11);

#POSTS
INSERT INTO  homeworker.posts
(userId , contents )
VALUES
(1, 'hello world!'),
(1, 'learning c++!'),
(1, 'i hate java'),
(2, 'i am high!'),
(2, 'go to the door!'),
(2, 'fluck!'),
(3, 'miscusi!'),
(3, 'fluck!'),
(3, 'mother of god!'),
(4, 'ashmalaxa!'),
(5, 'morte mio!');

#POST LIKES
INSERT INTO homeworker.postlike
(userId,postId,liked)
VALUES
(1,1,true),
(1,2,true),
(2,1,true),
(3,9,true),
(3,6,true),
(4,1,false),
(4,2,false),
(4,3,false),
(4,4,false),
(4,5,false),
(4,6,false);

#CATEGORY
INSERT INTO homeworker.categories
(name,description)
VALUES
('Jobs', 'Hire workforce or apply for a job!'),
('Life and Style', 'Daily posts from other homeworkers!'),
('Updates', 'System updates and important notifications.'),
('others  ' , 'everything else .'),
('WTF', 'Something funny.');

#POST CATEGORY

INSERT INTO homeworker.postcategory
(postId, categoryId)
VALUES
(1,1),
(2,1),
(3,2),
(4,2),
(5,2),
(6,2),
(7,3),
(8,4),
(9,4);

#comment
INSERT INTO homeworker.comment
(userId,postId,contents)
VALUES
(1,1,true),
(1,2,true),
(2,1,true),
(3,9,true),
(3,6,true),
(4,1,false),
(4,2,false),
(4,3,false),
(4,4,false),
(4,5,false),
(4,6,false);


#DELETE FROM homeworker.postcategory
#WHERE postcategory.id = 1;

########################SELECTS############################

#POSTS
select *
from homeworker.posts;

#USERS
 select *
 from homeworker.users;
#POST LIKES



#CATEGORIES
# SELECT categories.id, categories.name, categories.description
# FROM homeworker.categories
# WHERE categories.id = 1;

# UPDATE categories
# SET
#     categories.name = ?,
#     categories.description = ?
# WHERE
#     categories.id = ?;

#DELETE FROM homeworker.categories
#WHERE categories.id = 1;

#POST CATEGORIES
#
# SELECT post.*
# FROM homeworker.postcategory as postcategory
# JOIN homeworker.categories as category ON
# 	  postcategory.categoryId = category.id
# JOIN homeworker.posts as post ON
#      postcategory.postId = post.id
# WHERE category.id = 3;

# SELECT postcategory.id, postcategory.postId, postcategory.categoryId
# FROM homeworker.postcategory
# WHERE postcategory.postId = 1;
