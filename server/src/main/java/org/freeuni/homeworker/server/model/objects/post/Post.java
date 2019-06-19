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

    @JsonProperty("rating")
    private long rating;

    @JsonProperty("creationTimestamp")
    private Timestamp creationTimestamp;

    @JsonProperty("category")
    private String category;

    public Post() {

    }

    public Post(long id, long userId, String contents) {
        this.id = id;
        this.userId = userId;
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

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
