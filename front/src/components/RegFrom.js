import React, { Component } from 'react';
import axios from 'axios';

class RegFrom extends Component {
    state = {
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    };
    register = (e) => {
        e.preventDefault();
        let headers = ({
            'Access-Control-Allow-Origin' : '*',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Credentials' : 'true',
            'Access-Control-Allow-Methods' : 'POST, GET, OPTIONS, DELETE',
            'Access-Control-Max-Age' : '3600',
            'Access-Control-Allow-Headers' : 'Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
        });
        let request = JSON.stringify(this.state);
        axios.post("http://localhost/register", request, {headers: headers}).then((response) => {
            console.log(response);
        })
    };
    handleFirstNameChange = (e) => {
        this.setState({firstName: e.target.value});
    };
    handleLastNameChange = (e) => {
        this.setState({lastName: e.target.value});
    };
    handleEmailChange = (e) => {
        this.setState({email: e.target.value});
    };
    handlePasswordChange = (e) => {
        this.setState({password: e.target.value});
    };
    render() {
        return (
            <form action="post" name={"registrationForm"}>
                <label htmlFor="firstName">First Name:</label><input type="text" title="First Name" name="firstName" id="firstName" onChange={this.handleFirstNameChange}/><br/>
                <label htmlFor="lastName">Last Name:</label><input type="text" title="LastName" name="lastName" id="lastName" onChange={this.handleLastNameChange}/><br/>
                <label htmlFor="email">Email:</label><input type="email" title="Email" name="email" id="email" onChange={this.handleEmailChange}/><br/>
                <label htmlFor="password">Password:</label><input type="password" title="Password" name="password" id="password" onChange={this.handlePasswordChange}/><br/>
                <input type="submit" name="Register" onClick={this.register}/>
            </form>
        );
    }
}

export default RegFrom;