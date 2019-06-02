package org.freeuni.demo.server.database.comment;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class commentCreateObject implements Serializable {
    @JsonProperty("id")
    private long id;

    @JsonProperty("postId")
    private long postId;

    @JsonProperty("text")
    private String text;

    public void commentCreate(long id, long postId, String text){
        this.id = id;
        this.postId = postId;
        this.text = text;
    }
    public long getid() {
        return id;
    }
    public long getPostID() {
        return postId;
    }
    public String getCommentText(){
        return text;
    }
    public void changeCommentText(String text){
        this.text=text;
    }
}