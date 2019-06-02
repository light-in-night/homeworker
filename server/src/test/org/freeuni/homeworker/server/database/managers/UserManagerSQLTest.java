package org.freeuni.homeworker.server.database.managers;

import javafx.beans.binding.BooleanExpression;
import org.freeuni.homeworker.server.database.managers.user.BooleanWraper;
import org.freeuni.homeworker.server.database.managers.user.MockUserConnection;
import org.freeuni.homeworker.server.database.managers.user.MockUserPrepearedStatement;
import org.freeuni.homeworker.server.database.managers.user.MockUserResultSet;
import org.freeuni.homeworker.server.database.source.SQLConnectionFactory;
import org.freeuni.homeworker.server.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class UserManagerSQLTest {

    private UserManagerSQL userManagerSQL;

    private User validUser;

    private User invalidUser;

    private MockUserConnection connection;

    private MockUserResultSet resultSet;

    @Before
    public void setUp() {
        connection  = new MockUserConnection();
        userManagerSQL = new UserManagerSQL(Collections.<Connection>singletonList(connection));
        validUser = new User(1L, "Guga", "Tkesheladze", "Male", "email", "password");
        invalidUser = new User(1L, "Guga", "Tkesheladze", "Male", "email", null);
    }

    @Test(expected = IllegalStateException.class)
    public void UserManagerSQLConstructorTest1() {
        new UserManagerSQL(new ArrayList<Connection>());
    }


    @Test(expected = IllegalStateException.class)
    public void UserManagerSQLConstructorTest2() {
        new UserManagerSQL(null);
    }

    @Test
    public void UserManagerSQLTest3() {
        try {
            new UserManagerSQL(SQLConnectionFactory.getConnectionList(20));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userAddTest1() {
        Assert.assertTrue(userManagerSQL.addUser(validUser));
        MockUserPrepearedStatement preparedStatement = connection.getPreparedStatement();
        Assert.assertEquals(preparedStatement.getExecutedUpdate(), preparedStatement.getSql());
    }

    @Test
    public void userAddTest2() throws InterruptedException {
        int times = 300;
        final BooleanWraper booleanWraper = new BooleanWraper(true);
        final CountDownLatch countDownLatch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    booleanWraper.setValue(booleanWraper.isValue() && userManagerSQL.addUser(validUser));
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }

        countDownLatch.await();
        Assert.assertTrue(booleanWraper.isValue());
    }

    @Test
    public void getUserById() {
        User gotUser = userManagerSQL.getUserById(1);
        Assert.assertEquals(gotUser, validUser);
    }

    @Test
    public void getUserByEmail() {
        User gotUser = userManagerSQL.getUserByEmail("gtkes17@freuni.edu.ge");
        Assert.assertEquals(gotUser, validUser);
    }

    private List<Connection> getConnectionsList(int numConnections) {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < numConnections; i++) {
            connections.add(new MockUserConnection());
        }
        return connections;
    }
}