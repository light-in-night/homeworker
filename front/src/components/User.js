import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'

class User extends Component{
    constructor(props){
        super(props);
        this.state = {
            firstName : "",
            secondName : "",
            gender : "",
            email : "",
            karma : "",
            posts: [ {id : "", userId : "", contents : "", creationTimestamp : "" }]
        }     
    }
    getPosts(){
        var url = 'http://localhost/posts?userId='+localStorage.userId;
        fetch(url,{
                method: 'GET'
            }).then((response) =>{
                return response.json();
            })
            .then(function(myJson) {
                this.posts.setState(myJson);
              })
            .catch((error) => {
                console.log(error);
            });

    }
    getUser(){
        var url = 'http://localhost/admin/users?userId='+localStorage.userId;
        fetch(url,{
                method: 'GET'
            }).then((response) =>{
                return response.json();
            })
            .then(function(myJson) {
                this.setState(myJson);
              })
            .catch((error) => {
                console.log(error);
            });
            
    }
    render() {    
        return (
                
                <div>
                    <div id="sticky">
                        {this.getUser()} 
                        <form>                                
                            <p>name:{this.state.firstName} {this.state.secondName}</p>
                            <p>email:{this.state.email}</p>
                            <p>gender:{this.state.gender}</p>
                            <p>karma:{this.state.karma}</p>
                        </form>
                    </div>
                    <div id="text">
                        {this.getPosts()} 
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
export default User;
