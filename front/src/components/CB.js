import ChatBot from 'react-simple-chatbot';
import React, { Component } from 'react';
 class CB extends Component{
 steps = [
  {
    id: '0',
    message: 'Welcome to react chatbot!',
    trigger: '1',
  },
  {
    id: '1',
    user:true
  },
];
 
render(){
    return (
  <div>
    <ChatBot steps={this.steps} />
  </div>

);
 } }
 export default CB;