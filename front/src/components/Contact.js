import React, { Component } from 'react';
import '../App.css';

class Contact extends Component{

    handleUserRequest() {
        //Handle SomeHow
    }

    render() {
        return (
          <div>
              <form className={"contactForm"} onSubmit={this.handleUserRequest}>
                  <ul className="contactList">
                      <li>
                          <h4  style={{margin: "auto"}}>Tell Us What We Can Do For U</h4>
                      </li>
                      <li>
                          <label htmlFor={"email"}>Email</label>
                          <input type="email" id="email" placeholder="Enter Your Email Here"/>
                      </li>
                      <li>
                          <label htmlFor="text">Your Opinion</label>
                          <textarea placeholder="Tell Us Anything You Wish. We Do Best For You Excitement" cols="20" rows="10"/>
                      </li>
                      <li>
                          <button type="submit">Submit</button>
                      </li>
                  </ul>
              </form>
          </div>  
        );
    }
}

export default Contact;