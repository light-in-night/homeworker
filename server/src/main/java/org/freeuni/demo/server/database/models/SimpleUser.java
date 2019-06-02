package org.freeuni.demo.server.database.models;

public class SimpleUser implements User {

    private final long id;
    private final String name;

    SimpleUser(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }
}
