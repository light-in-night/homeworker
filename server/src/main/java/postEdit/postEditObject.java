package postEdit;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class postEditObject implements Serializable {
    // ID of the post
    @JsonProperty("id")
    private int postID ;

    //inside text of the post  , for editing post we only
    //need id and text  , not depending on full table
    @JsonProperty("postText")
    private String postText;

    //constructor
    public postEditObject(int id , String text){
        this.postID = id ;
        this.postText = text;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
