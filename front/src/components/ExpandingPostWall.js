import React, { Component } from 'react';
import PostBox from './PostBox';
import '../App.css';

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
    
    fetchData = () => {
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
                            <div className="category-item">
                                <p>{post.contents}</p>
                            </div>)
                }
            </div>
          </div>  
        );
    }
}

export default ExpandingPostWall