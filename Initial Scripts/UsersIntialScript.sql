DROP TABLE users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL ,
    last_name VARCHAR(100) NOT NULL ,
    gender VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE ,
    password VARCHAR(100) NOT NULL
);