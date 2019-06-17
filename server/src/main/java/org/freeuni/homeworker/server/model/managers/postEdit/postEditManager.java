package org.freeuni.homeworker.server.model.managers.postEdit;

import org.freeuni.homeworker.server.model.objects.postEdit.postEditObject;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class postEditManager implements postEdit {
    private final String EDIT_POST = "update posts Set content = ? , lastUpdated = now() where postId = ?";
    // Gets postEdit object and sets column data for given object id to text in database
    private final ConnectionPool connectionsPool;

    public postEditManager(ConnectionPool connectionsPool){
        this.connectionsPool = connectionsPool;
    }
    @Override
    public void editPost(postEditObject obj) {
        Connection con = null;
        try {
            con = connectionsPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(EDIT_POST);
            preparedStatement.setString(1, obj.getPostText());
            preparedStatement.setLong(2, obj.getPostID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                connectionsPool.putBackConnection(con);
            }

        }


    }
}
