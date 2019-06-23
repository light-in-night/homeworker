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
        if(!this.validateUser()){
            console.error("Fields are invalid.")
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
        let valid = this.state.firstName !== "";
        valid = valid && this.state.email !== "";  
        valid = valid && this.state.password !== "";
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