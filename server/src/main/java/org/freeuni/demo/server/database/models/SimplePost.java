package org.freeuni.demo.server.database.models;

public class SimplePost implements Post {
    private final long id;
    private final User author;
    private final String content;

    SimplePost(long id,User author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public long getId() {
        return id;
    }
}
