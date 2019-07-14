import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import App from "../App";

class Post extends Component{
    constructor(props){
        super(props);
        this.state = {
            id: "",
            firstName : "",
            lastName : "",
            gender : "",
            email : "",
            karma : "",
            posts: []
        }     
    }
    
    prepareInfo(){
        
    }


    
    componentDidMount(){
        this.prepareInfo();
    }

    textStyle = {
        color: "#021a40"
    };
    render() {
        return (
                
                <div>
                    <div id="sticky">
                        <form>                                
                            <p style={this.textStyle}>{this.state.firstName}</p>
                            <p style={this.textStyle}>{this.state.lastName}</p>
                            <p style={this.textStyle}>gender:{this.state.gender}</p>
                            <p style={this.textStyle}>karma:{this.state.karma}</p>
                        </form>
                    </div>
                    <div id="text">
                        {this.state.posts
                            .map(post => 
                                <Link to={{pathname : '/posts', state : {source: `http://localhost/posts?postId=${post.postId}`, } }} 
                                    style={{ textDecoration: 'none'}}>
                                        <div className="post-item">
                                            <p>{post.contents}</p>
                                        </div>
                                </Link> 
                            )}
                    </div>
               </div>
               
        );
    }
}
export default Post;
