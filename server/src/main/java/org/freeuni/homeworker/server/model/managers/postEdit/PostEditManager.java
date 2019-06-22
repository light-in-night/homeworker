package org.freeuni.homeworker.server.model.managers.postEdit;

import org.freeuni.homeworker.server.model.managers.GeneralManagerSQL;
import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;
import org.freeuni.homeworker.server.model.source.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostEditManager extends GeneralManagerSQL implements PostEdit {

    private final String EDIT_POST = "UPDATE posts SET content = ? where id = ?";
    // Gets postEdit object and sets column data for given object id to text in database

    public PostEditManager(ConnectionPool connectionsPool) {
        super(connectionsPool);
    }

    @Override
    public void editPost(PostEditObject obj) {
        Connection con = null;
        try {
            con = connectionPool.acquireConnection();
            PreparedStatement preparedStatement = con.prepareStatement(EDIT_POST);
            preparedStatement.setString(1, obj.getPostText());
            preparedStatement.setLong(2, obj.getPostID());
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                connectionPool.putBackConnection(con);
            }

        }
    }
}
