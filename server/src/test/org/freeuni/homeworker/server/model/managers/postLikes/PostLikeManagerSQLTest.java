package org.freeuni.homeworker.server.model.managers.postLikes;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Before;
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

    private PostLike postLike;
    @Before
    public void setUp() throws InterruptedException, SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        postLike = new PostLike(1,2,3,true);

        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false).thenReturn(false);
        when(connectionPool.acquireConnection()).thenReturn(connection);
    }
    @Test
    public void like() throws InterruptedException, SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException()).thenReturn(1);
        when(connectionPool.acquireConnection()).thenReturn(connection);
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.like(postLike);
        postLikeManagerSQL.like(postLike);
    }

    @Test
    public void unLike() throws SQLException, InterruptedException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException()).thenReturn(1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        PostLikeManagerSQL postLikeManagerSQL = new PostLikeManagerSQL(connectionPool);
        postLikeManagerSQL.unLike(postLike);
        postLikeManagerSQL.unLike(postLike);
    }
}