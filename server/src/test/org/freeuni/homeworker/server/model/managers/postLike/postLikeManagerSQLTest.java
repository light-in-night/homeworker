package org.freeuni.homeworker.server.model.managers.postLike;

import org.freeuni.homeworker.server.database.managers.postLike.Base;
import org.freeuni.homeworker.server.database.managers.postLike.MockPostLikeConnection;
import org.freeuni.homeworker.server.database.managers.postLike.MockPostLikeResultSet;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManager;
import org.freeuni.homeworker.server.model.managers.postLikes.PostLikeManagerSQL;
import org.freeuni.homeworker.server.model.managers.users.UserManagerSQL;
import org.freeuni.homeworker.server.model.objects.postLike.PostLike;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.freeuni.homeworker.server.model.source.ConnectionPoolFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class postLikeManagerSQLTest {


    private PostLike postLike;
    private PostLikeManager postLikeManager;
    private Connection connection;

    MockPostLikeResultSet resultSet;


    @Before
    public void init(){
        connection = new MockPostLikeConnection();
        resultSet = new MockPostLikeResultSet();
        postLikeManager = new PostLikeManagerSQL(ConnectionPoolFactory.buildConnectionPool(Collections.<Connection>singletonList(connection)));
        postLike = new PostLike();
        postLike.setLiked(true);
        postLike.setPostID(2);
        postLike.setUserID(3);
    }

    @Test(expected = IllegalStateException.class)
    public void UserManagerSQLConstructorTest1() {
        new PostLikeManagerSQL(ConnectionPoolFactory.buildConnectionPool(new ArrayList<Connection>()));
    }

    @Test
    public void testLike(){
        assertEquals(0, Base.base.size());
        assertTrue(postLikeManager.like(postLike));
        assertEquals(1, Base.base.size());
        assertTrue(postLikeManager.like(postLike));
        assertEquals(1, Base.base.size());
        assertTrue(postLikeManager.unLike(postLike));
        assertEquals(0, Base.base.size());
        assertTrue(postLikeManager.unLike(postLike));
        assertEquals(0, Base.base.size());
    }

}
