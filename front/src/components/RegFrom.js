import React, { Component } from 'react';

class RegFrom extends Component {
    state = {
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    };
    register = (e) => {
        e.preventDefault();
        let request = JSON.stringify(this.state);
        fetch('http://localhost/register', {
            method: 'POST',
            body: request
        }).then((response) => {
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