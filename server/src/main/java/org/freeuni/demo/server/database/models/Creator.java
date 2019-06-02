package org.freeuni.demo.server.database.models;

import java.sql.ResultSet;
import java.util.List;

public interface Creator{

    Post createPost(long user_id, String username, long post_id, String content);

    List<Post> postsFromResultSet(ResultSet rs);
}
