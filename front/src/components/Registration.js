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
                <form onSubmit={this.register} >
                 <span>pst i can help you register</span><a href='/chatBot' ><img src='chatbot.png' width={40} height={40} alt={"Couldn't Find Resource"}/></a><br/><br/><br/>
                
                <ul className="regFormOuter">
                    <li>
                        <label htmlFor="first-name">First Name</label>
                        <input type="text" id="first-name" placeholder="Enter your first name here" onChange={this.handleFirstNameChange} required />
                    </li>
                    <li>
                        <label htmlFor="last-name">Last Name</label>
                        <input type="text" id="last-name" placeholder="Enter your last name here" onChange={this.handleLastNameChange} required />
                    </li>
                    <li>
                        <label htmlFor="gender">Gender</label>
                        <input type="text" id="gender" placeholder="Enter your gender here" onChange={this.handleGenderChange} required />
                    </li>
                    <li>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" placeholder="Enter your email here" onChange={this.handleEmailChange} required />
                    </li>
                    <li>
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" placeholder="Enter your password here" onChange={this.handlePasswordChange} required />
                    </li>
                    <li>
                        <label htmlFor="repeatPassword">Repeat Password</label>
                        <input type="password" id="repeatPassword" placeholder="Repeat your password here" onChange={this.handleRepeatPasswordChange} required />
                    </li>
                    <li>
                        <button type="submit" >Register</button>
                    </li>
                </ul>
                </form>
               
            </div>
        );
    }
}

export default Registration;