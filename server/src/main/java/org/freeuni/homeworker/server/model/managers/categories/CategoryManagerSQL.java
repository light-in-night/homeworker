package org.freeuni.homeworker.server.model.managers.categories;

import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.category.CategoryFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CategoryManagerSQL implements CategoryManager {
    private static final String ADD_CATEGORY =
            "INSERT INTO homeworker.categories\n" +
                "(name,description)\n" +
                "VALUES\n" +
                "(?, ?);";

    private static final String REMOVE_CATEGORY =
            "DELETE FROM homeworker.categories\n" +
                "WHERE categories.id = ?;";

    private static final String SELECT_BY_ID =
            "SELECT categories.id, categories.name, categories.description\n" +
                "FROM homeworker.categories\n" +
                "WHERE categories.id = ?;";
    private static final String SELECT_ALL =
            "SELECT  *" +
                    "FROM  homeworker.categories;";

    private final ConnectionPool connectionPool;

    public CategoryManagerSQL(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Category category) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CATEGORY);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public void removeById(long id) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CATEGORY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public Category getById(long id) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CategoryFactory.fromResultSet(resultSet);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CategoryFactory.listFromResultSet(resultSet);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
        return null;
    }

}
