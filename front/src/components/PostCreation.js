import React, { Component } from 'react';
import '../App.css';

class PostCreation extends Component{
    render() {
        return (
            <div>
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
                <button type="submit" onClick={this.register}>Register</button>
                </li>
            </ul>
            </form>
            </div>  
        );
    }
}

export default About