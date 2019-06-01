package org.freeuni.demo.server.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlCreator implements Creator {

    @Override
    public Entry fromResultSet(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            Map<String,Object> data = new HashMap<String,Object>();

            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                data.put(rsmd.getColumnClassName(i), rs.getObject(i));
            }

            return new MySQLEntry(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Database makeDatabase() {
        return new MySQLDatabase(makeSource(), makeQuery(), this);
    }

    @Override
    public Query makeQuery() {
        return new MySQLQuery();
    }

    @Override
    public Source makeSource() {
        return new MySQLSource();
    }
}
