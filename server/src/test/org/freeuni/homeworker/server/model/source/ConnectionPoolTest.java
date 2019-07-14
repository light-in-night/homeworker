package org.freeuni.homeworker.server.model.source;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ConnectionPoolTest {
    @Mock
    Connection con1, con2;

    List<Connection> cons;

    ConnectionPool pool;
    @Before
    public void setUp() {
        cons = Arrays.asList(con1,con2);
        pool = new ConnectionPool(cons);
    }

    @Test(expected = IllegalStateException.class)
    public void ConstructorFail() {
        cons = new ArrayList<>();
        pool = new ConnectionPool(cons);
    }

    @Test
    public void acquireConnection() throws InterruptedException {
        pool.acquireConnection();
        pool.acquireConnection();
    }

    @Test
    public void acquireConnectionNull() throws InterruptedException {
        pool.destroy();
        assertNull(pool.acquireConnection());
    }


    @Test
    public void putBackConnection() throws InterruptedException {
        pool.acquireConnection();
        pool.putBackConnection(pool.acquireConnection());
        pool.acquireConnection();
    }

    @Test
    public void destroy() {
        pool.destroy();
    }
    @Test
    public void destroyFailOnClose() throws SQLException {
        doThrow(new SQLException())
                .when(con1).close();
        pool.destroy();
    }

}