package org.freeuni.homeworker.server.model.objects.postEdit;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DB object. also can be converted to JSON string
 * with JSON annotations.
 *
 * Deprecated : insead use PostManager.updatePostContents
 */
@Deprecated
public class PostEditObject implements Serializable {
    // ID of the post
    @JsonProperty("id")
    private long postID ;

    //inside text of the post  , for editing post we only
    //need id and text  , not depending on full table
    @JsonProperty("postText")
    private String postText;

    //constructor
    public PostEditObject(long id , String text){
        this.postID = id ;
        this.postText = text;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public long getPostID() {
        return postID;
    }
}

