package org.freeuni.demo.server.database;

import java.util.HashMap;
import java.util.Map;

public class MySQLEntry implements Entry {
    Map<String,Object> data;
    public MySQLEntry(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public Object getColumn(String colName) {
        return data.get(colName);
    }
}
