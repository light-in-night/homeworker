package org.freeuni.homeworker.server.model.source;

import org.freeuni.homeworker.server.model.source.rawSource.SQLConnectionFactory;
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