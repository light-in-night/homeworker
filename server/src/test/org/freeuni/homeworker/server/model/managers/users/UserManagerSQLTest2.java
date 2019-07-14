package org.freeuni.homeworker.server.model.managers.users;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.model.source.ConnectionPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerSQLTest2 {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private ResultSet resultSet;

    private User user;
    private User user2;

    @Before
    public void setUp() throws InterruptedException, SQLException {
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        user = new User(1,"dato","koka","male","dkoka","password");
        user2 = new User(2,"davit","kokaia","male","dkoka17","pass");
        long k =4;
        user.setKarma(k);
        user2.setKarma(k);

        when(resultSet.getLong(1)).thenReturn(user.getId()).thenReturn(user2.getId());
        when(resultSet.getString(2)).thenReturn(user.getFirstName()).thenReturn(user2.getFirstName());
        when(resultSet.getString(3)).thenReturn(user.getLastName()).thenReturn(user2.getLastName());
        when(resultSet.getString(4)).thenReturn(user.getGender()).thenReturn(user2.getGender());
        when(resultSet.getString(5)).thenReturn(user.getEmail()).thenReturn(user2.getEmail());
        when(resultSet.getString(6)).thenReturn(user.getPassword()).thenReturn(user2.getPassword());

    }
    @Test
    public void addUser() throws InterruptedException, SQLException {
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        UserManagerSQL userManagerSQL = new UserManagerSQL(connectionPool);
        userManagerSQL.addUser(user);
    }

    @Test
    public void getUserById() throws SQLException, InterruptedException {
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        UserManagerSQL userManagerSQL = new UserManagerSQL(connectionPool);
        User help = userManagerSQL.getUserById(1);
        Assert.assertEquals(help,user);
    }

    @Test
    public void getUserByEmail() throws InterruptedException, SQLException {
        when(connectionPool.acquireConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        UserManagerSQL userManagerSQL = new UserManagerSQL(connectionPool);
        User help = userManagerSQL.getUserByEmail("dkoka");
        Assert.assertEquals(help,user);
    }

    @Test
    public void getUsers() throws InterruptedException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(connectionPool.acquireConnection()).thenReturn(null).thenThrow(new InterruptedException()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException()).thenReturn(preparedStatement);
        UserManagerSQL userManagerSQL = new UserManagerSQL(connectionPool);
        List<User> help = userManagerSQL.getUsers(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        Assert.assertEquals(help,list);
    }

    @Test
    public void destroyManager() {
        UserManagerSQL userManagerSQL = new UserManagerSQL(connectionPool);
        userManagerSQL.destroyManager();
    }
}