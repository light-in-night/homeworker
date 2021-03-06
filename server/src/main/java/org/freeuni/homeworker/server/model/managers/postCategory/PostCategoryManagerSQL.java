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
    private static final String SELECT_POSTS_BY_CATEGORY =
            "SELECT post.*\n" +
                    "FROM homeworker.postcategory as postcategory\n" +
                    "JOIN homeworker.categories as category ON\n" +
                    "\t  postcategory.categoryId = category.id\n" +
                    "JOIN homeworker.posts as post ON\n" +
                    "    postcategory.postId = post.id\n" +
                    "WHERE category.id = ?;";
    private static final String SELECT_CATEGORIES_BY_POST =
            "SELECT category.*\n" +
                    "FROM homeworker.postcategory as postcategory\n" +
                    "JOIN homeworker.categories as category ON\n" +
                    "\t  postcategory.categoryId = category.id\n" +
                    "JOIN homeworker.posts as post ON\n" +
                    "    postcategory.postId = post.id\n" +
                    "WHERE post.id = ?;";


    private static final String COUNT_NUM_POSTS = "SELECT COUNT(*) FROM postcategory WHERE categoryId=?;";

    public PostCategoryManagerSQL(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void add(PostCategory postCategory) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_POST_CATEGORY);
            preparedStatement.setLong(1, postCategory.getPostId());
            preparedStatement.setLong(2, postCategory.getCategoryId());
            preparedStatement.executeUpdate();
        }finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public void removeById(long id) throws InterruptedException, SQLException {
        executeQuery(id, REMOVE_POST_CATEGORY);
    }

    @Override
    public PostCategory getByPostId(long postId) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_POST_ID);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return PostCategoryFactory.postCategoryFromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<PostCategory> getByCategoryId(long categoryId) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CATEGORY_ID);
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostCategoryFactory.listFromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }


    @Override
    public List<Post> getPostsInCategory(long categoryId) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_POSTS_BY_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return PostFactory.listFromResultSet(resultSet);
        }  finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public List<Category> getCategoriesOfPost(long postId) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIES_BY_POST);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return CategoryFactory.listFromResultSet(resultSet);
        }finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    @Override
    public long getCountNumberOfPostsByCategory(long categoryId) throws InterruptedException, SQLException {
        Connection connection = null;
        try{
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(COUNT_NUM_POSTS);
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } finally {
            if(connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }

    private void executeQuery(long postId, String removeByPostid) throws InterruptedException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(removeByPostid);
            preparedStatement.setLong(1, postId);
            preparedStatement.executeUpdate();
        } finally {
            if (connection != null) {
                connectionPool.putBackConnection(connection);
            }
        }
    }


}
