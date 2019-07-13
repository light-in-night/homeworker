import React, { Component } from 'react';
import '../App.css';

class UserComments extends Component{
    constructor(){
        super();
        this.state = {
            userId : "1",
            postId : "1",
            content : "works"
        }     
    }

   
    getCommentsBYUser(){
        var url = 'http://localhost/getcomment/byuser?userId='+localStorage.userId;
        fetch(url,{
                method: 'GET'
            }).then((response) =>{
                console.log(response);
                return response.json();
            })
            .then(function(myJson) {
                console.log(myJson);
              })
            .catch((error) => {
                console.log(error);
            });
    }

    getCommentsBYPost(post_id){
        console.log("posr");
        var url = 'http://localhost/getcomment/bypost?postId='+post_id;
        fetch(url,{
                method: 'GET'
            }).then((response) =>{
                console.log(response);
                return response.json();
            })
            .then(function(myJson) {
                console.log(myJson);
              })
            .catch((error) => {
                console.log(error);
            });
    }

    createPost(e){
        console.log(this.state);
        console.log("yeaaa");
        console.log(this.state);
        let request = JSON.stringify(this.state);
        fetch('http://localhost/createComment', {
            method: 'POST',
            body: request
        })
        .catch((error) => {
            console.log(error);
        });     
       
    }


    makeContent = (e) => {
        this.setState({content: e.target.value});
        console.log(this.state);
    }
    makePostId = (e) => {
        
        this.setState({userId: localStorage.userId});
        
        
        this.setState({postId: e.target.value});
        console.log(this.state);
    }

    render() {
        this.createPost(this);
        return (
            <div>
            <label>ssss</label>
        </div>
        );
    }

    /*
    render() {
        this.getCommentsBYUser();
        return (
            <div>
              
            <form  onSubmit={this.createPost.bind(this)}>
            <ul className="loginFormList">
            <li>
                        <label htmlFor="content">content</label>
                        <input type="text" id="content" placeholder="write a comment" onChange={this.makeContent}/>
                    </li>

                    <li>
                        <label htmlFor="postId">postId</label>
                        <input type="text" id="postId" placeholder="Enter post id" onChange={this.makePostId}/>
                    </li>

                    <li>
                        <button type="submit">Click me</button>
                    </li>
            </ul>
        </form>
              
        </div>
        );
    }
*/
}

export default UserComments;
