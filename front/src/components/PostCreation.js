import React, { Component } from 'react';
import '../App.css';

class PostCreation extends Component{

    constructor(props) {
        super(props);
        
        this.state =  {
            userId : 1,         //TODO: change this
            contents : "",
            category : ""

        };
        this.makePost = this.makePost.bind(this);
        this.handleContentChange = this.handleContentChange.bind(this);
        this.handleCategoryChange = this.handleCategoryChange.bind(this);
        this.routeChange = this.routeChange.bind(this);
      }

    makePost(e) {
        e.preventDefault();
        let request = JSON.stringify(this.state);
        fetch('http://localhost:8080/server_war_exploded/createpost', {
            method: 'POST',
            body: request
        }).then((response) => {
            console.log(response);
        })
    };

    handleContentChange(e){
        this.setState({contents: e.target.value});
    };

    handleCategoryChange(e){
        this.setState({category: e.target.value});
    };

    routeChange() {
        let path = 'home';
        this.props.history.push(path);
    }

    render() {
        return (
            <div>
            <form>
            <ul className="regFormOuter">
                <li>
                    <label htmlFor="contents">Post Contents</label>
                    <textarea type="text" id="contents" placeholder="Tell us a story..." cols="20" rows="10" onChange={this.handleContentChange}/>
                </li>
                <li>
                    <label htmlFor="category">Post Category</label>
                    <input type="text" id="category" placeholder="What's your post's category?" onChange={this.handleCategoryChange}/>
                </li>
                <li>
                    <button type="submit" onClick={(event) => { this.makePost(event); this.routeChange();}}>Publish</button>
                </li>
            </ul>
            </form>
            </div>  
        );
    }
}

export default PostCreation