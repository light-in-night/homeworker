package org.freeuni.homeworker.server.model.objects.category;

import org.freeuni.homeworker.server.model.objects.post.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryFactory {
    public static Category fromResultSet(ResultSet resultSet)  {
        try {
            Category category = new Category();
            category.setId(resultSet.getLong("id"));
            category.setName(resultSet.getString("name"));
            category.setDescription(resultSet.getString("description"));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Category> listFromResultSet(ResultSet resultSet) {
        List<Category> result = new ArrayList<>();
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
