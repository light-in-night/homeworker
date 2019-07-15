package org.freeuni.homeworker.server.model.objects.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    @JsonProperty("id")
    private long id;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("postId")
    private long postId;

    @JsonProperty("contents")
    private String contents;

    public Comment() {

    }

    public Comment(long id, long userId, long postId, String contents) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getUserId() {
        return userId;
    }

    public long getPostId() {
        return postId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    public void setPostId(long post_id) {
        this.postId = post_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
