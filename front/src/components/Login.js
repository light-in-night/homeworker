import React, {Component} from 'react';
import App from "../App";

class Login extends Component {
    constructor(props){
        super(props);
        this.state = {
            email : "",
            password : "",
        }
    }
    
    

    login (e) {
        e.preventDefault();
        if(this.validateUser() === false){
            //Change Login header
        } else {
            let request = JSON.stringify(this.state);
            App.getUserSessionId( (sessionId) =>
                fetch('http://localhost/hasSession/login', {
                    method: 'POST',
                    headers: { 'sessionId': sessionId },
                    body: request
                }).then((response) => {
                    response.json().then((data) => {
                        console.log(data);
                        if (data.STATUS === 'OK') {
                            console.log('User has been logged successfully');
                            this.redirectPage('/User');
                        } else {
                            alert("something is not right , try again better maaan");
                            console.log('User was not logged in, ' + data.ERROR_MESSAGE);
                        }
                    })
                }))
        }
    }
    
    redirectPage(page){
        this.props.history.push(page);
    }

    validateUser = () => {
        this.setState( {
            email : this.state.email.trim(),
            password : this.state.password.trim()
        })
        let valid = this.state.email.length > 0 &&
        this.state.password.length >= 0;
        return valid;
    }
    
    handleEmailChange = (e) => {
        this.setState({email: e.target.value});
    };
    handlePasswordChange = (e) => {
        this.setState({password: e.target.value});
    };
    
    render() {     
        return (
            <div class="loginbody">
<form onSubmit={this.login.bind(this)} class="formaa">
<div class="box">
<h1 class="log">Login</h1>
<input type="email" name="email"   class="email"  onChange={this.handleEmailChange.bind(this)} placeholder="email..." required /> <br></br>
<input type="password" name="password"  placeholder="password..." onChange={this.handlePasswordChange.bind(this)}  class="password"  required />
  
<button type="submit"  class="btn">Login</button>
   
            </div>
            </form>   
            </div>
            
        );
    }
}

export default Login;