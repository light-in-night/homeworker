package org.freeuni.homeworker.server.database.source;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class SQLConnectionFactoryTest {

    @Test
    public void getConnection() {
        try {
            assertNotNull(SQLConnectionFactory.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}