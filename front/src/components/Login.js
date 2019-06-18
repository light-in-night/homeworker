import React, { Component } from 'react';

class Login extends Component {
    constructor(){
        super();
        this.state = {
            firstName : "",
            lastName : "",
            phoneNumber : "",
            email : "",
            password : "",
            repeatedPassword : "", 
        }

    }
    
    

    login = (e) => {
        e.preventDefault();
        if(this.validateUser() === false){
            //Inform That Data Is Not Enough
            return;
        }
        if(this.state.password !== this.state.repeatedPassword){
            //Inform That Password Mismatch
            return;
        }
        let request = JSON.stringify(this.state);
        console.log(request);
        return;

        fetch('http://localhost/login', {
            method: 'POST',
            body: request
        }).then((response) => {
            console.log(response);
        })
    };
    
    validateUser = () => {
        let valid = this.state.firstName !== "";
        valid = valid && this.state.lastName !== "";
        valid = valid && this.state.phoneNumber !== "";
        valid = valid && this.state.email !== "";  
        valid = valid && this.state.password !== "";
        valid = valid && this.state.repeatedPassword !== "";
        return valid;
    }
    
    handleFirstNameChange = (e) => {
        this.setState({firstName: e.target.value});
    };
    handleLastNameChange = (e) => {
        this.setState({lastName: e.target.value});
    };
    handlePhoneNumberChange = (e) => {
        this.setState({lastName : e.target.value});
    }
    handleEmailChange = (e) => {
        this.setState({email: e.target.value});
    };
    handlePasswordChange = (e) => {
        this.setState({password: e.target.value});
    };
    handleRepeatPasswordChange = (e) => {
        this.setState({password : e.target.value});
    }
    
    render() {
        return (
            <div>
            <form>
            <ul className="loginForm">
                <li>
                    <label htmlFor="first-name">First Name</label>
                    <input type="text" id="first-name" placeholder="Enter your first name here" onChange={this.handleFirstNameChange}/>
                </li>
                <li>
                    <label htmlFor="last-name">Last Name</label>
                    <input type="text" id="last-name" placeholder="Enter your last name here" onChange={this.handleLastNameChange}/>
                </li>
                <li>
                    <label htmlFor="phone-number">Phone Number</label>
                    <input type="text" id="phone-number" placeholder="Enter your Phone Number here" onChange={this.handlePhoneNumberChange}/>
                </li>
                <li>
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" placeholder="Enter your email here" onChange={this.handleEmailChange}/>
                </li>
                <li>
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" placeholder="Enter your password here" onChange={this.handlePasswordChange}/>
                </li>
                <li>
                    <label htmlFor="repeat-password">Repeat Password</label>
                    <input type="password" id="repeat-password" placeholder="Repeat your password here" onChange={this.handleRepeatPasswordChange}/>
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