import React, {Component} from 'react';
import '../App.css';
import { Link } from 'react-router-dom'

/*
gets postCategory id

from home categories

and goes to localhost and gets  posts by id from there

this.state.source will get that id

*/

class ExpandingPostWall extends Component{
    constructor(props){
        super(props);
        this.state = {
            STATUS : "loading",
            ERROR_MESSAGE : "",

            source : props.location.state.source,
            posts : [],
        }
        this.componentDidMount = this.componentDidMount.bind(this);

    }

    /*
    fetches data from api
    and adds posts to state

    */

    fetchData = () => {
        console.log(this.state.source);
        console.log("aqvar");
        fetch(this.state.source)
        .then(response => response.json())
        .then(jsonObj => this.setState(jsonObj))
        .catch(error =>
            console.log(error)
        )
    }


    componentDidMount(){
        this.fetchData();
    }


    render() {
        console.log(this.state);

        return (
          <div>
              <div className="category-box" >
                {this.state.posts
                            .map(post => 
                                <Link to={{pathname : '/Post', state : {source: `http://localhost/posts?id=${post.id}`, } }} 
                                    style={{ textDecoration: 'none'}} key={post.id}>
                                        <div className="post-item">
                                            <p className={"contents"}>{post.contents}</p>
                                                <p className={"likes"}>Likes: {post.numLikes}</p>
                                                <p className={"comments"}>Comments: {post.numComments}</p>

                                        </div>
                                </Link> 
                            )}
            </div>
          </div>  
        );
    }
}

export default ExpandingPostWall ; 