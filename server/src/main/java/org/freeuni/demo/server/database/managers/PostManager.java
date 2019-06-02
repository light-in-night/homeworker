package org.freeuni.demo.server.database.managers;

import org.freeuni.demo.server.database.models.Post;

import java.util.List;

public interface PostManager  {

    void addPost(Post pst);
    List<Post> byUserId(long user_id);
}
