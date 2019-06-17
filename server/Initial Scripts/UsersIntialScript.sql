#DROP DATABASE IF EXISTS homeworker;
#CREATE DATABASE homeworker;
USE homeworker;

DROP TABLE IF EXISTS users;

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

