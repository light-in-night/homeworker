import React, { Component } from 'react';

class Registration extends Component {
    state = {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        repeatPassword: ""
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
    handleRepeatPasswordChange = (e) => {
        this.setState({repeatPassword: e.target.value})
    }
    
    render() {
        return (
            <div id='registrationForm'>
                <form>
                <ul className="regFormOuter">
                    <li>
                        <label htmlFor="first-name">First Name</label>
                        <input type="text" id="first-name" placeholder="Enter your first name here" onChange={this.handleFirstNameChange}/>
                    </li>
                    <li>
                        <label htmlFor="last-name">Last Name</label>
                        <input type="text" id="last-name" placeholder="Enter your last name here" onChange={this.handleLastNameChange}/>
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
                        <label htmlFor="repeatPassword">Repeat Password</label>
                        <input type="password" id="repeatPassword" placeholder="Repeat your password here" onChange={this.handleRepeatPasswordChange}/>
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