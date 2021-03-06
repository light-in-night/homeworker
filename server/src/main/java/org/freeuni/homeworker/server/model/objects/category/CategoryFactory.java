package org.freeuni.homeworker.server.model.objects.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Static factory class. every "new" opeation goes here.
 * can also create lists when needed.
 */
public class CategoryFactory {

    private static final Logger log = LoggerFactory.getLogger(CategoryFactory.class);

    /**
     * Makes a single object from resultSet.
     * @param resultSet resultSet of the object
     * @return object on successful conversion, null otherwise
     */
    public static Category fromResultSet(ResultSet resultSet)  {
        try {
            Category category = new Category();
            category.setId(resultSet.getLong("id"));
            category.setName(resultSet.getString("name"));
            category.setDescription(resultSet.getString("description"));
            return category;
        } catch (SQLException e) {
            log.error("Error occurred during converting result set to category.", e);
        }
        return null;
    }

    /**
     * Makes a list of objects from resultSet.
     * @param resultSet resultSet of the object
     * @return list of objects on successful conversion, null otherwise
     */
    public static List<Category> listFromResultSet(ResultSet resultSet) {
        List<Category> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(fromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.error("Error occurred during transforming result set to list of categories.", e);
        }
        return null;
    }

    /**
     * Wraps category object in objectNode and returns node.
     *
     * @param category category object that you want to convert to JSON
     * @param node ObjectNode that will be filled with category JSON
     * @return same node, with added JSON
     */
    public static ObjectNode toObjectNode(Category category, ObjectNode node) {
        node.put("id", category.getId());
        node.put("name", category.getName());
        node.put("description", category.getDescription());
        node.put("count", category.getCount());
        return node;
    }
}
