package org.freeuni.homeworker.server.model.objects.postLike;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


/**
 * DB object. also can be converted to JSON string
 * with JSON annotations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // annotation to include null values when converted to json
public class PostLike implements Serializable {

    @JsonProperty("id")
    private long id;

    @JsonProperty("userID")
    private Long userID;

    @JsonProperty("postID")
    private Long postID;

    @JsonProperty("liked")
    private Boolean liked;

    /**
     * Constructs PostLike Object, Which Contains Information, That
     * usedeID Liked Post Which Id Is postID, Or Opposite
     * @param id user ID
     * @param userID User ID
     * @param postID Post ID
     * @param liked Shows Whether User Liked, Or Disliked The Post
     */
    public PostLike(long id, Long userID, Long postID, Boolean liked) {
        this.id = id;
        this.userID = userID;
        this.postID = postID;
        this.liked = liked;
    }

    public PostLike(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("id : " + getId() + " ");
        toString.append("User ID : " + getUserID() + " ");
        toString.append("Post ID : " + getPostID() + " ");
        toString.append("Is Liked : " + isLiked());

        return toString.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserID(), getPostID(), isLiked());
    }


    public boolean containsNullFields(){
        return userID == null ||
                postID == null ||
                liked == null;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other){
            return true;
        }
        if( !(other instanceof PostLike) ){
            return false;
        }

        PostLike that = (PostLike) other;
        return this.getId() == that.getId() &&
                this.getPostID() == that.getPostID() &&
                this.getUserID() == that.getUserID() &&
                this.isLiked() == that.isLiked();
    }
}
