package org.freeuni.demo.server.database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelCreator implements Creator{
    @Override
    public Post createPost(long user_id, String user_name, long post_id, String content) {
        User author = new SimpleUser(user_id, user_name);
        return new SimplePost(post_id, author, content);
    }

    @Override
    public List<Post> postsFromResultSet(ResultSet rs) {
        List<Post> result = null;
        try {
            result = new ArrayList<>();
            while (rs.next()) {
                long post_id = rs.getLong(1);
                String post_content = rs.getString(2);
                long user_id = rs.getLong(3);
                String user_name = rs.getString(4);
                result.add(createPost(user_id, user_name, post_id, post_content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
