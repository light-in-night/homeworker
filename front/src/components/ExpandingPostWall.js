import React, { Component } from 'react';
import PostBox from './PostBox';
import '../App.css';

class ExpandingPostWall extends Component{
    constructor(props){
        super(props);
        this.state = {
            source : props.location.state.source,
            items : []
        }
        this.componentDidMount = this.componentDidMount.bind(this);
    
    }
    
    fetchData = () => {
        fetch(this.state.source)
        .then((response) => response.json())
        .then((obj) => {
            this.setState({
                items : obj
            })
        })
        .catch((error) => {
            console.log(error);
        })
    }

    componentDidMount(){
        this.fetchData();
    }

    render() {
        console.log(this.state.items);
        
        return (
          <div>
              <PostBox posts={this.state.items}/>
          </div>  
        );
    }
}

export default ExpandingPostWall