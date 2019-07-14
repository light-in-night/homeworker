import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import { IoIosMail } from 'react-icons/io';
import App from "../App";

class User extends Component{
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
        App.getUserSessionId((sessionId)=>{
            var url = 'http://localhost//hasSession/isLoggedIn/privateUserInfo';
            fetch(url,{
                method: 'GET',
                headers: { 'sessionId': sessionId }
            }).then((response)=>response.json())
            .then(myJson=>{
                console.log(myJson);
                this.setState(myJson);
            })
            .then( x =>{
                url = 'http://localhost/posts?userId='+this.state.id;
                fetch(url,{
                    method: 'GET'
                }).then((response) => response.json())
                .then(myJson=> {
                    this.setState(myJson);
                  })
                .catch((error) => {
                    console.log(error);
                });
            })
        });    
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
                            <p style={this.textStyle}><IoIosMail />:{this.state.email}</p>
                            <p style={this.textStyle}>gender:{this.state.gender}</p>
                            <p style={this.textStyle}>karma:{this.state.karma}</p>
                        </form>
                    </div>
                    <div id="text">
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
export default User;
