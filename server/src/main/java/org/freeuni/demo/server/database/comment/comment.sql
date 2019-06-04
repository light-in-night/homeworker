USE city;
  
DROP TABLE IF EXISTS comment;
 -- remove table if it already exists and start from scratch

CREATE TABLE comment (
    id BIGINT primary key,
    userId BIGINT,
    postId BIGINT,
    commentText varchar(369)
);

INSERT INTO comment VALUES
(1,1,1,"good"),
(2,1,2,"like"),
(3,1,3,"shit"),
(4,2,1,"nice"),
(5,2,2,"fuck you");
