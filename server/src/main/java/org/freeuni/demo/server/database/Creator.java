package org.freeuni.demo.server.database;

import java.sql.ResultSet;

public interface Creator {
    Entry fromResultSet(ResultSet rs);
    Database makeDatabase();
    Query makeQuery();
    Source makeSource();
}
