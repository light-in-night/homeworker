package org.freeuni.homeworker.server.model.objects.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostFactory {
    //TODO: what if resultset is invalid?
    public static Post postFromResultSet(ResultSet resultSet)  {
        try {
            Post post = new Post();
            post.setId(resultSet.getLong("id"));
            post.setUserId(resultSet.getLong("userId_FK"));
            post.setContents(resultSet.getString("content"));
            post.setCreationtimestamp(resultSet.getTimestamp("creationtimestamp"));
            post.setCategory(resultSet.getString("category"));
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Post> postListFromResultSet(ResultSet resultSet) {
        List<Post> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(postFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
