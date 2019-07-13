package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostLikeManagerSQLTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    @Test
    public void rateFirstTime() throws InterruptedException, SQLException {
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
 //       PostLikeManager postEditManager = new PostLikeManager();


        /*
        when(connectionPool.acquireConnection()).thenThrow(new InterruptedException()).thenReturn(connection).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
         */
    }

    @Test
    public void getByUserAndPost() {
    }

    @Test
    public void rateNextTime() {
    }

    @Test
    public void getByPost() {
    }
}