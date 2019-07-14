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
            posts: []
        }     
    }
    getPosts(){
        var url = 'http://localhost/posts?userId='+localStorage.userId;
        fetch(url,{
                method: 'GET'
            }).then((response) => response.json())
            .then(myJson=> {
                console.log(myJson);
                this.setState(myJson);
              })
            .catch((error) => {
                console.log(error);
            });

    }
    getUser(){
        var url = 'http://localhost/admin/users?userId='+localStorage.userId;
        fetch(url,{
                method: 'GET'
            }).then((response) => response.json())
            .then(myJson=> {
                console.log(myJson);
                this.setState(myJson);
              })
            .catch((error) => {
                console.log(error);
            });
            
    }
    componentDidMount(){
        this.getUser();
        this.getPosts();
    }
    render() {   

        return (
                
                <div>
                    <div id="sticky">
                        <form>                                
                            <p>name:{this.state.firstName} {this.state.secondName}</p>
                            <p>email:{this.state.email}</p>
                            <p>gender:{this.state.gender}</p>
                            <p>karma:{this.state.karma}</p>
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
export default User;
