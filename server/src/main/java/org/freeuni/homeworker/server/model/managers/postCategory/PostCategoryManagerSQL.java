package org.freeuni.homeworker.server.model.managers.postCategory;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.category.CategoryFactory;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.post.PostFactory;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategoryFactory;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostCategoryManagerSQL extends GeneralManagerSQL implements PostCategoryManager {

    private static final String ADD_POST_CATEGORY =
            "INSERT INTO homeworker.postcategory\n" +
                    "(postId,categoryId)\n" +
                    "VALUES\n" +
                    "(?,?);";
    private static final String REMOVE_POST_CATEGORY =
            "DELETE FROM homeworker.postcategory\n" +
                    "WHERE postcategory.id = ?;";
    private static final String SELECT_BY_POST_ID =
            "SELECT *\n" +
                    "FROM homeworker.postcategory\n" +
                    "WHERE postcategory.postId = ?;";
    private static final String SELECT_BY_CATEGORY_ID =
            "SELECT *\n" +
                    "FROM homeworker.postcategory\n" +
                    "WHERE postcategory.categoryId = ?;";
    private static final String COUNT_POSTS_BY_CATEGORY =
            "SELECT COUNT(postcategory.id)\n" +
                    "FROM homeworker.postcategory as postcategory\n" +
                    "JOIN homeworker.categories as category ON\n" +
                    "\t  postcategory.categoryId = category.id\n" +
                    "JOIN homeworker.posts as post ON\n" +
                    "    postcategory.postId = post.id\n" +
                    "WHERE category.id = ?;";
    private static final String COUNT_CATEGORIES_BY_POST =
            "SELECT COUNT(postcategory.id)\n" +
                    "FROM homeworker.postcategory as postcategory\n" +
                    "JOIN homeworker.categories as category ON\n" +
                    "\t  postcategory.categoryId = category.id\n" +
                    "JOIN homeworker.posts as post ON\n" +
                    "    postcategory.postId = post.id\n" +
                    "WHERE post.id = ?;";


    public PostCategoryManagerSQL(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void add(PostCategory postCategory) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_POST_CATEGORY);
            preparedStatement.setLong(1, postCategory.getPostId());
            preparedStatement.setLong(2, postCategory.getCategoryId());
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
    public void removeById(long id) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_POST_CATEGORY);
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
    public List<PostCategory> getByPostId(long postId) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_POST_ID);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostCategoryFactory.listFromResultSet(resultSet);
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
    public List<PostCategory> getByCategoryId(long categoryId) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CATEGORY_ID);
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostCategoryFactory.listFromResultSet(resultSet);
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
    public List<Post> getPostsInCategory(long categoryId) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_POSTS_BY_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
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
    public List<Category> getCategoriesOfPost(long postId) {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CATEGORIES_BY_POST);
            preparedStatement.setLong(1, postId);
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
