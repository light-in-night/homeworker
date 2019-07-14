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
            posts: [],
            userId: null,
            comments: [],
            text : "",
            source: props.location.state.source
        }
    }
    
    prepareInfo(){
        let url = this.state.source;
        console.log(url)
        fetch(url,{
            method: 'GET'
        }).then((response) => response.json())
        .then(myJson=> {
            this.setState(myJson);
            this.setState({userId : myJson.posts[0].userId})
        })
        .then(x=>{
            url = 'http://localhost/users?id='+this.state.userId;
            console.log(url);
            fetch(url,{
                method: 'GET'
            })
            .then((response) => {
                console.log(response)

            })
        });
    }

    do(){
        App.getUserSessionId((sessionId) => {
            var url ='http://localhost/users?id='+1;
            console.log(url)
            fetch(url, {
                method : 'GET',
                headers: {
                    'sessionId' : sessionId
                }
            }).then((response) => {
                console.log(response)
                response.json().then((parsed) => {
                    console.log(parsed)
                })
            })
        })
    }
    componentDidMount(){
        this.do();
    }

    render() {
        console.log(this.state);
        return (
                <div>
                   <div>
                        {this.state.posts
                            .map(post => 
                                <Link to={{pathname : '/Post', state : {source: `http://localhost/posts?id=${post.id}`, } }} 
                                    style={{ textDecoration: 'none'}} key={post.id}>
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
