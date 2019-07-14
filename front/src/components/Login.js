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
            <div id="loginBody">
            <form  onSubmit={this.login.bind(this)}>
                <ul className="loginFormList">
                    <li>
                        <h3 className="loginHeader">Enter Your Account Information Here</h3>
                    </li>
                    <li>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" placeholder="Enter Your Email Here" onChange={this.handleEmailChange} required />
                    </li> 

                    <li>
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" placeholder="Enter Your Password Here" onChange={this.handlePasswordChange} required />
                    </li>
                    <li>
                        <button type="submit">Login</button>
                    </li>
                </ul>
            </form>
            </div>
        );
    }
}

export default Login;