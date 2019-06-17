package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.source.rawSource.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostLikeManagerSQL implements PostLikeManager {

    private final String SELECT_ALL = "SELECT * FROM postLike WHERE userID = ? AND postID = ?;";
    private final String DELETE_QUERY = "DELETE FROM postLike WHERE userID = ? AND postID = ?;";
    private final String ADD_QUERY = "INSERT INTO postLike (userID, postID, liked) VALUES (?, ?, ?);";
    private final ConnectionPool connectionsPool;

    public PostLikeManagerSQL(ConnectionPool connectionsPool){
        this.connectionsPool = connectionsPool;
    }

    /**
     * Adds Like Information In DataBase
     * @param postLikeObject Like Information(userID, postID)
     * @return true If Added Successfully
     */
    public boolean like(PostLike postLikeObject) {
        boolean queryExecuted;
        Connection connection = null;
        try {
            connection = connectionsPool.acquireConnection();
            if (userAlreadyLikedPost(postLikeObject, connection)){
            } else {
                executeAddSQLQuery(postLikeObject, connection);
            }
            queryExecuted = true;

        } catch (Exception e) {
            e.printStackTrace();
            queryExecuted = false;
        } finally {
            if (connection != null) {
                connectionsPool.putBackConnection(connection);
            }
        }
        return queryExecuted;
    }

    /**
     * Checks Whether There Is Already Like From User On The Post
     * @param postLikeObject Post Information Object
     * @param connection connection
     * @return true if User Already Liked The Post
     * @throws SQLException
     */
    private boolean userAlreadyLikedPost(PostLike postLikeObject, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
        preparedStatement.setLong(1,postLikeObject.getUserID());
        preparedStatement.setLong(2, postLikeObject.getPostID());
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        return resultSet.next();
    }

    /**
     * Executes Given Query
     * @param postLikeObject Information To Based On To Execute(postId, UserID)
     */
    private void executeAddSQLQuery(PostLike postLikeObject, Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY);
        preparedStatement.setLong(1, postLikeObject.getUserID());
        preparedStatement.setLong(2, postLikeObject.getPostID());
        preparedStatement.setBoolean(3,postLikeObject.isLiked());
        preparedStatement.executeUpdate();
    }

    /**
     * Removes Post Like Information From DataBase
     * @param postLikeObject UnLike Information(userID, postID)
     * @return true If Query Executed Successfully
     */
    public boolean unLike(PostLike postLikeObject) {
        boolean queryExecuted;
        Connection connection = null;
        try {
            connection = connectionsPool.acquireConnection();
            executeDeleteSQLQuery(postLikeObject, connection);
            queryExecuted = true;
        } catch (Exception e) {
            e.printStackTrace();
            queryExecuted = false;
        } finally {
            if (connection != null) {
                connectionsPool.putBackConnection(connection);
            }
        }
        return queryExecuted;
    }

    /**
     * Executes Delete Query
     * @param postLikeObject
     * @param connection
     * @throws SQLException
     */
    private void executeDeleteSQLQuery(PostLike postLikeObject, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setLong(1,postLikeObject.getUserID());
        preparedStatement.setLong(2, postLikeObject.getPostID());
        preparedStatement.executeUpdate();
    }

}
