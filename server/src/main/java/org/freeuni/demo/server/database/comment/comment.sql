USE city;
  
DROP TABLE IF EXISTS comment;
 -- remove table if it already exists and start from scratch

CREATE TABLE comment (
    id BIGINT primary key,
    postId BIGINT,
    commentText varchar(369)
);

INSERT INTO comment VALUES
(1,1,"good"),
(2,1,"like"),
(3,1,"shit"),
(4,2,"nice"),
(5,2,"fuck you");
