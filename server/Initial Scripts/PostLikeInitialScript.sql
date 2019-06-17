#DROP DATABASE IF EXISTS homeworker;
#CREATE DATABASE homeworker;
USE homeworker;

DROP TABLE IF EXISTS postLike;

CREATE TABLE postLike(
    id BIGINT AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    postId BIGINT NOT NULL,
    liked BOOLEAN NOT NULL,
    CONSTRAINT pkId primary key (id),
	foreign key (userId) references users(id),
	foreign key (postId) references posts(id)
);