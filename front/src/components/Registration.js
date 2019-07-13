import React, { Component } from 'react';

class Registration extends Component {
    state = {
        firstName: "",
        lastName: "",
        gender: "",
        email: "",
        password: "",
        repeatPassword: ""
    };

    register = (e) => {
        e.preventDefault();
        e.target.disabled = true;
        let request = JSON.stringify(this.state);
        fetch('http://localhost/users', {
            method: 'POST',
            body: request
        }).then((response) => {
            if(response.status !== "OK") {
                console.log(response);
            } else {
                this.routeChange();
            }
        }).catch((error) => {
            console.log(error)
        })
    };

    routeChange = () => {
        let path = 'home';
        this.props.history.push(path);
    }

    handleFirstNameChange = (e) => {
        this.setState({firstName: e.target.value});
    };

    handleLastNameChange = (e) => {
        this.setState({lastName: e.target.value});
    };
    
    handleGenderChange = (e) => {
        this.setState({gender: e.target.value});
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
                <form >
                 <span>pst i can help you register</span><a href='/chatBot' ><img src='chatbot.png' width={40} height={40}></img></a><br/><br/><br/>
                
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
                        <label htmlFor="gender">Gender</label>
                        <input type="text" id="gender" placeholder="Enter your gender here" onChange={this.handleGenderChange}/>
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
                        <button type="submit" onSubmit={this.register}>Register</button>
                    </li>
                </ul>
                </form>
               
            </div>
        );
    }
}

export default Registration;