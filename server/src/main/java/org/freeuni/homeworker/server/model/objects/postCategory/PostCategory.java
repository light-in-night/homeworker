package org.freeuni.homeworker.server.model.objects.postCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCategory {
    @JsonProperty("id")
    private long id;

    @JsonProperty("postId")
    private long postId;

    @JsonProperty("categoryId")
    private long categoryId;

    public PostCategory() {

    }

    public PostCategory(long id, long postId, long categoryId) {
        this.id = id;
        this.postId = postId;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
