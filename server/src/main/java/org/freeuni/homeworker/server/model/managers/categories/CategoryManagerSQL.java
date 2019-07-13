package org.freeuni.homeworker.server.model.managers.categories;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.category.CategoryFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.*;
import java.util.List;



public class CategoryManagerSQL extends GeneralManagerSQL implements CategoryManager {
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
    private static final String UPDATE_STATEMENT =
            "UPDATE categories\n" +
                    "SET\n" +
                    "    categories.name = ?,\n" +
                    "    categories.description = ?\n" +
                    "WHERE\n" +
                    "    categories.id = ?;";


    public CategoryManagerSQL(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public long add(Category category) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public void removeById(long id) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CATEGORY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public Category getById(long id) throws SQLException, InterruptedException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CategoryFactory.fromResultSet(resultSet);
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<Category> getAllCategories() throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CategoryFactory.listFromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public void modifyCategory(Category category) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATEMENT);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setLong(3, category.getId());
            preparedStatement.executeUpdate();
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }
}
