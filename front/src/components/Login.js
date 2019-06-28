import React, { Component } from 'react';

class Login extends Component {
    constructor(){
        super();
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
            fetch('http://localhost/login', {
                method: 'POST',
                body: request
            }).then((response) => {
                response.json()
                .then((data) => {
                    //Not TestIng sessionId... Yet;
                    if(data.loggedIn){
                        localStorage.setItem("userId", data.userId);
                        //Redirect SomeWhere Else When Done
                        this.redirectPage("/");   
                    } else {
                        console.log(data);
                        let header = document.getElementsByClassName("loginHeader");   
                        header.innerHTML = "Email Or Password Was Not Correct";
                    }
                })
            })
            .catch((error) => {
                console.log(error);
            });
        }       
        
    };
    
    redirectPage(page){
        this.props.history.push(page);
    }

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
            <form  onSubmit={this.login.bind(this)}>
                <ul className="loginFormList">
                    <li>
                        <h3 className="loginHeader">Enter Your Account Information Here</h3>
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
                        <button type="submit">Login</button>
                    </li>
                </ul>
            </form>
            </div>
        );
    }
}

export default Login;