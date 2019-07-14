import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import { IoIosMail } from 'react-icons/io';

class User extends Component{
    constructor(props){
        super(props);
        this.state = {
            firstName : "dato",
            secondName : "kokaia",
            gender : "male",
            email : "dkoka17@freeuni.edu.ge",
            karma : "5",
            posts: [{contents : "good job"},
            {contents : "nice"},
            {contents : "tsl"},
            {contents : "I want to say, that it is fucking importans"},
            {contents : "good good boy"},
            {contents : "tsl"},
            {contents : "I want to say, that it is fucking importans"},
            {contents : "good good boy"},
            {contents : "tsl"},
            {contents : "I want to say, that it is fucking importans"},
            {contents : "good good boy"},
            {contents : "tsl"},
            {contents : "I want to say, that it is fucking importans"},
            {contents : "good good boy"},
            {contents : "tsl"},
            {contents : "I want to say, that it is fucking importans"},
            {contents : "good good boy"},
        ]
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

    textStyle = {
        color: "#021a40"
    };
    render() {   

        return (
                
                <div>
                    <div id="sticky">
                        <form>                                
                            <p style={this.textStyle}>name:{this.state.firstName} {this.state.secondName}</p>
                            <p style={this.textStyle}><IoIosMail />:{this.state.email}</p>
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
export default User;
