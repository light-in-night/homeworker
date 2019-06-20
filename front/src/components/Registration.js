import React, { Component } from 'react';

class Registration extends Component {
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
            <div>
            <form>
            <ul className="regFormOuter">
                <li>
                    <label htmlFor="postTitle">First Name</label>
                    <input type="text" id="postTitle" placeholder="Post Title" onChange={this.handleFirstNameChange}/>
                </li>
                <li>
                    <label htmlFor="last-name">Last Name</label>
                    <input type="text" id="lastName" placeholder="Enter your last name here" onChange={this.handleLastNameChange}/>
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
                    <button type="submit" onClick={this.register}>Register</button>
                </li>
            </ul>
            </form>
            </div>
        );
    }
}

export default Registration;