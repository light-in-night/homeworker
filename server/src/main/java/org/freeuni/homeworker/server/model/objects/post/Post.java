package org.freeuni.homeworker.server.model.objects.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;


@JsonInclude(JsonInclude.Include.NON_NULL) // annotation to (not) include null values when converted to json
public class Post {

    @JsonProperty("id")
    private long id;

    @JsonProperty("user_id")
    private long user_id;

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

    public Post(long id, long user_id, String contents) {
        this.id = id;
        this.user_id = user_id;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
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
