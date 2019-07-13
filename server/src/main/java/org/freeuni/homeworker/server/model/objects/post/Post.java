package org.freeuni.homeworker.server.model.objects.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;


/**
 * DB object. also can be converted to JSON string
 * with JSON annotations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // annotation to (not) include null values when converted to json
public class Post {

    @JsonProperty("id")
    private long id;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("contents")
    private String contents;

    @JsonProperty("creationTimestamp")
    private Timestamp creationTimestamp;

    public Post() {

    }

    public Post(long id, long userId, String contents) {
        this.id = id;
        this.userId = userId;
        this.contents = contents;
    }

    public Post(int id, int userId, String contents, Timestamp creationTimestamp) {

        this.id = id;
        this.userId = userId;
        this.contents = contents;
        this.creationTimestamp = creationTimestamp;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

}
