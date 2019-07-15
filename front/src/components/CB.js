import ChatBot from 'react-simple-chatbot';
import React, { Component } from 'react';
 class CB extends Component{
  state = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    repeatPassword: ""
  };
  register = () => {
  
    let request = JSON.stringify(this.state);
    fetch('http://localhost/users', {
        method: 'POST',
        body: request
    }).then((response) => {
        if(response.status !== "OK") {
            console.log(response);
        } else {
            this.routeChange();
        }
    }).catch((error) => {
        console.log(error)
    })
};
  steps = [
    {
      id: '0',
      message: 'Welcome i will help you register , how should i adress you? ^^',
      trigger: 'name',
    },
    {
      id:'name',
      user:true , 
      trigger :'2'
    },
    {
      id: '2',
      message: 'hi {previousValue} , lets begin ! , first , tell me your first name',
      trigger: 'firstName',
    }, {
      id : 'firstName',
      user:true , 
      validator:(value) => {
        if(value===''){
          return 'you cant have blank name silly !';
        }else{
          console.log(this.state.name)
          this.setState({firstName:value});
          return true;
        }
      }
      ,trigger:'lastNamebot'
      },
      {
        id:'lastNamebot',
        message:'okay , now tell me your last name please' , 
        trigger:'lastName'
      },
      {
        id:'lastName' , 
        user:true , 
        validator:(value) => {
          if(value===''){
            return 'you cant have blank last name !!';
          }else{
            console.log(this.state.name)
            this.setState({lastName:value});
            console.log(this.state.name)
            return true;
          }
        },trigger:'emailBot'
      },
      {
        id:'emailBot' , 
        message: ' give me your email please  ' ,
        trigger:'email'
      },
      {
        id:'email' , 
        user:true , 
        validator:(value) =>{
          if(value.indexOf('@')===-1){
              return 'please give a valid mail';
          }else{
            this.setState({email:value});
            return true;
          }
        },
        trigger:'passwordBot'
      },
      {
        id:'passwordBot' , 
        message:'alright , almost finished , password left , choose it wisely ' ,
        trigger:'password'
      },
      {
        id:'password',
        user:true , 
        validator:(value) => {
          if(value===''){
            return 'empty password , rly???'
          }else{
            this.setState({password:value}) ;
            return true;
          }
        },
        trigger:'repeatPasswordBot' 
      }
      ,
      {
        id:'repeatPasswordBot',
        message:'repeat Password Please',
        trigger:'repeatPassword'
      },
      {
        id:'repeatPassword',
        user:true , 
        validator:(value)=>{
            if(value===this.state.password){
                this.setState({repeatPassword:value});
                return true;
            }else{
              return 'passwords dont match , please repeat correctly';
            }
        },
        trigger:'almostThere'
      },
      {
        id:'almostThere' , 
        message: 'almost there , type anything to register',
        trigger: 'almoost',
      },
      {
        id: 'almoost',
        user:true,
        validator:(value)=>{
          if(true){
            console.log(this.state);
            this.register();
            return true;
            
          }
        }
        ,end:true
        

      }
      
  ];

 
 
render(){
    return (
  <div className="chatbot">
    <ChatBot  className="chatbot" steps={this.steps} />
  </div>

);
 } }
 export default CB;