import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import { IoIosMail } from 'react-icons/io';
import App from "../App";

class visitUser extends Component{
    constructor(props){
        super(props);
        this.state = {
            
            source: props.location.state.source,
        }     
    }
    
    render() {
        console.log(this.source);
        return (
                
                <h1>ass</h1>
               
        );
    }
}
export default visitUser;
