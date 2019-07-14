import React, { Component } from 'react';
import '../App.css';

class Contact extends Component{


    constructor(props){
        super(props);
        this.state = {
            submitted : false,
        }
        this.handleUserRequest = this.handleUserRequest.bind(this);

    }

    handleUserRequest() {
        this.setState({
            submitted : true,
        })
    }

    render() {
        if(this.state.submitted === true){
            return (
                <div>
                    <h1 className={"thankUser"}>Thank You!</h1>

                </div>
            );
        }else {
            return (
                <div>
                    <form className={"contactForm"} onSubmit={this.handleUserRequest}>
                        <br/>
                        <ul className="contactList">
                            <li>
                                <h4  style={{margin: "auto"}}>Tell Us What We Can Do For You</h4>
                            </li>
                            <li>
                                <label htmlFor={"email"}>Email</label>
                                <input type="email" id="email" placeholder="Enter Your Email Here" required={true}/>
                            </li>
                            <li>
                                <label htmlFor="text">Your Opinion</label>
                                <textarea placeholder="Tell Us Anything You Wish. We Do Best For You Excitement" cols="20" rows="10" required={true}/>
                            </li>
                            <li>
                                <button>Submit</button>
                            </li>
                        </ul>
                    </form>
                </div>
            );
        }
    }
}

export default Contact;