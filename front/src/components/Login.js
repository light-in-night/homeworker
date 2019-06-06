import React, { Component } from 'react';

class Login extends Component {
    constructor(){
        super();
        this.state = {
            email : "",
            password : "",
        }
    }
    
    

    login = (e) => {
        e.preventDefault();
        if(this.validateUser() === false){
            
            return;
        }
        if(this.state.password !== this.state.repeatedPassword){
            //Inform That Password Mismatch
            return;
        }

        let request = JSON.stringify(this.state);
        console.log(request);
        return;

        // fetch('http://localhost:8080/login', {
        //     method: 'POST',
        //     body: request
        // }).then((response) => {
        //     console.log(response);
        // })
    };
    
    validateUser = () => {
        this.state.email = this.state.email.trim();
        this.state.password = this.state.password.trim();
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
            <div id="loginBody">
            <form>
            <ul className="loginFormList">
                <li>
                    <h3 className="loginHeader">Enter Yout Account Infiromation Here</h3>
                </li>
                <li>
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" placeholder="Enter Your Email Here" onChange={this.handleEmailChange}/>
                </li>
                <li>
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" placeholder="Enter Your Password Here" onChange={this.handlePasswordChange}/>
                </li>
                <li>
                <button type="submit" onClick={this.login}>Login</button>
                </li>
            </ul>
            </form>
            </div>
        );
    }
}

export default Login;