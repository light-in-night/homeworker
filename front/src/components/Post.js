import React, { Component } from 'react';
import '../App.css';
import App from "../App";
import { Link } from 'react-router-dom'

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
            numLikes:"",
            posts: [],
            userId: null,
            postId: null,
            comments: [],
            source: props.location.state.source,
            CommentNumber : 1,
            text : ""
        }
    }
    
    prepareInfo(){
        var url = this.state.source;
        fetch(url,{
            method: 'GET'
        }).then((response) => response.json())
        .then(myJson=> {
            this.setState(myJson);
            this.setState({userId : myJson.posts[0].userId})
            this.setState({postId : myJson.posts[0].id})
            this.setState({numLikes : myJson.posts[0].numLikes})
        })
        .then(x=>{
            url = 'http://localhost/users/userAccess?id='+this.state.userId;
            fetch(url,{
                method: 'GET'
            })
            .then((response)=>response.json())
            .then(myJson=>{
                this.setState(myJson.users[0]);
            });
        })
        .then(y=>{
            url = 'http://localhost/getComments?postId='+this.state.postId+'&numComments='+this.state.CommentNumber;
            fetch(url,{
                method: 'GET'
            })
            .then((response)=>response.json())
            .then(myJson=>{
                console.log(url);
                console.log(this.state);
                console.log(myJson);
                this.setState(myJson);
            });
        });
    }  
    componentDidMount(){
       this.prepareInfo();
    }
    addComment = (e) =>{
       let request = {
           postId: this.state.postId,
           userId: this.state.userId,
           contents: this.state.text 
        
        }
        let json = JSON.stringify(request);
        
        App.getUserSessionId((sessionId)=>{
            var url = 'http://localhost/hasSession/isLoggedIn/createComment';
            fetch(url,{
                method: 'POST',
                body: json,
                headers: { 'sessionId': sessionId }
            })
        });    
    }
    changeComment = (e) => {
        this.setState({text: e.target.value})
    }
    loadMore = (e) =>{
        let x = this.state.CommentNumber;
        x+=10;
        this.setState({CommentNumber : x});

        var url = 'http://localhost/getComments?postId='+this.state.postId+'&numComments='+this.state.CommentNumber;
        fetch(url,{
                method: 'GET'
            })
            .then((response)=>response.json())
            .then(myJson=>{
                this.setState(myJson);
            });
    }
    textStyle = {
        color: "#021a40"
    };
    render() {
        return (
                <div className="postDiv">
                         <div className="postUserDiv">

                        <p className="postUserName" style={this.textStyle}>name: {this.state.firstName}</p>




                            <p  className="postUserLastName" style={this.textStyle}>lastName: {this.state.lastName}</p>



                    </div>
                   <div  className="postUserPost" >
                        {this.state.posts
                            .map(post => 
                                <Link to={{pathname : '/Post', state : {source: `http://localhost/posts?id=${post.id}`, } }} 
                                    style={{ textDecoration: 'none'}} key={post.id}>
                                        <div className="drop-shadow ">
                                            <p>{post.contents}</p>
                                        </div>
                                </Link> 
                            )}
                    </div>
                    <div  className="postUserMesseges">
                        {this.state.comments
                            .map(Comment => 
                                <div className="post-msg">
                                    <p>
                                    {Comment.contents}</p>
                                </div>
                            )}

                            <button  className="postUserbtn" type="loadMore" onClick={this.loadMore}>loadMore</button>

                    </div>  
                    <div className="davigale">

                            <textarea rows="4" cols="30" className="msgInput" type="text" id="contents" placeholder="Enter your opinion" onChange={this.changeComment} required />

                            <button className="postUserbtn" type="add" onClick={this.addComment}>addComment</button>

                    </div>
               </div>
               
        );
    }
}
export default Post;
