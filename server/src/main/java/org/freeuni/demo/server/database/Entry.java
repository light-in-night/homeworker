package org.freeuni.demo.server.database;

import java.sql.ResultSet;

public interface Entry {
    Object getColumn(String colName);
}
