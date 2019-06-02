package org.freeuni.demo.server.database.models;

public interface Post {
    long getId();
    User getAuthor();
    String getContent();
}
