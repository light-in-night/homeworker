import React, {Component} from 'react';
import PostItem from "./PostItem";
import '../App.css';

class PostBox extends Component {
    constructor(props){
        super(props);
        console.log("props:" + props);
        
        this.state = {
            posts: this.props.posts
        };
    }

    render() {
        console.log(this.state.posts);
        return (
            <div className="category-box" >
                {this.state.posts.map((posts) => {
                  return (<PostItem userName={posts.userName} content={posts.content} />);
                })}
            </div>
        );
    }
}

export default PostBox;