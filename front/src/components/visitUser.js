import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import { IoIosMail } from 'react-icons/io';
import App from "../App";

class visitUser extends Component{
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
            source: props.location.state.source
        }     
    }
    
    prepareInfo(){
            var url = this.state.source;

            fetch(url,{
                method: 'GET'
            }).then((response)=>response.json())
            .then(myJson=>{
                this.setState(myJson.users[0]);
                console.log(url);
                console.log(myJson);
                console.log(this.state);
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
                        <form className={"userInfo"}>
                            <ul className={"userInfoList"}>
                                <li>
                                    <p style={this.textStyle}>Name: {this.state.firstName}</p>
                                </li>
                                <li>
                                    <p style={this.textStyle}>LastName: {this.state.lastName}</p>
                                </li>
                                <li>
                                    <p style={this.textStyle}><IoIosMail />Mail: {this.state.email}</p>
                                </li>
                                <li>
                                    <p style={this.textStyle}>gender: {this.state.gender}</p>
                                </li>
                                <li>
                                    <p style={this.textStyle}>karma: {this.state.karma}</p>
                                </li>
                                <li>
                                <div>
                      <Link to={{pathname : '/asd/'+this.state.id }} 
                                   >{this.state.firstName} {this.state.lastName}</Link>
                           </div>  
                                </li>
                            </ul>

                        </form>
                    </div>
                    <div id="text">
                        <h2 className={"userPosts"}>Posts</h2>

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
export default visitUser;
