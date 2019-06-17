package org.freeuni.homeworker.server.model.objects.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostFactory {
    //TODO: what if resultset is invalid?
    public static Post fromResultSet(ResultSet resultSet)  {
        try {
            Post post = new Post();
            post.setId(resultSet.getLong("id"));
            post.setUserId(resultSet.getLong("userId"));
            post.setContents(resultSet.getString("content"));
            post.setCreationTimestamp(resultSet.getTimestamp("creationtimestamp"));
            post.setCategory(resultSet.getString("category"));
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Post> listFromResultSet(ResultSet resultSet) {
        List<Post> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(fromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
