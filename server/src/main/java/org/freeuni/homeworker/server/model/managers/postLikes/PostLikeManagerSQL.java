package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostLikeManagerSQL implements PostLikeManager {

    private final String DELETE_QUERY = "DELETE FROM PostLikeTable WHERE userID = ? AND postID = ?;";
    private final String ADD_QUERY = "INSERT INTO PostLikeTable (?, ?, ?, ?);";
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
            executeAddSQLQuery(postLikeObject, ADD_QUERY, connection);
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
     * Executes Given Query
     * @param postLikeObject Information To Based On To Execute(postId, UserID)
     * @param addQuery query
     */
    private void executeAddSQLQuery(PostLike postLikeObject, String addQuery, Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
        preparedStatement.setLong(1,postLikeObject.getId());
        preparedStatement.setLong(2, postLikeObject.getUserID());
        preparedStatement.setLong(3, postLikeObject.getPostID());
        preparedStatement.setBoolean(4,postLikeObject.isLiked());
        preparedStatement.execute();
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
            executeDeleteSQLQuery(postLikeObject, DELETE_QUERY, connection);
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
     * @param delete_query
     * @param connection
     * @throws SQLException
     */
    private void executeDeleteSQLQuery(PostLike postLikeObject, String delete_query, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(delete_query);
        preparedStatement.setLong(1,postLikeObject.getId());
        preparedStatement.setLong(2, postLikeObject.getUserID());
        preparedStatement.execute();
    }

}
