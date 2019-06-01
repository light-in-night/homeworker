package org.freeuni.demo.server.database;

import java.util.List;

public interface Database {
    void addEntry(Entry e);
    List<Entry> getEntries(Query q);
}
