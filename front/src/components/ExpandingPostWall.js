import React, {Component} from 'react';
import '../App.css';

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
        };
        this.componentDidMount = this.componentDidMount.bind(this);
    
    }

    /*
    fetches data from api 
    and adds posts to state

    */

    fetchData = () => {
        fetch(this.state.source)
        .then(response => response.json())
        .then(jsonObj => this.setState(jsonObj))
        .catch(error => 
            console.log(error)
        )
    };


    componentDidMount(){
        this.fetchData();
    }


    render() {

        return (
          <div>
              <div className="category-box" >
                {this.state.posts
                    .map(post => 
                            <div className="category-item" key={Math.random()}>
                                <p>{post.contents}</p>
                            </div>)
                }
            </div>
          </div>  
        );
    }
}

export default ExpandingPostWall ; 