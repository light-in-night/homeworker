package org.freeuni.demo.server.database.source;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SQLConnectionFactoryTest {

    @Test
    public void getConnection() {
        try {
            assertNotNull(new SQLConnectionFactory().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}