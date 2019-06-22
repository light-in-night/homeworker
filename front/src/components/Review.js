import React, { Component } from 'react';
import '../App.css'

class Review extends Component{
    constructor(props){
        super(props);
        this.state={
            text:"" , 
            label :'Please Submit Your Review'
        };
    }

    handleSubmit= (event) =>{
        event.preventDefault();
        var frm = document.getElementsByName('area')[0];
        frm.value='';
        this.setState({text:'' , label:'Thanks for Review ^^'});
    }
  
    render () {
        return (
            <div className="review">
                <form onSubmit={this.handleSubmit}>
        <label>
          {this.state.label} </label><br></br>
          <textarea name='area' placeholder="write text here ..."  rows={4} cols={50}/><br></br>
        <input type="submit" value="Submit review" />
      </form>
            </div>
        )
    }
     
}
export default Review;