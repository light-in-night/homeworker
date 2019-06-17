import React, { Component } from 'react';
import { BrowserRouter, Route, Link, NavLink } from 'react-router-dom';
import '../App.css';

class SideMenu extends Component {
    constructor(props){
        super(props);

        this.state = {
            containerOpened:false,
            containerMaxWidth:"30%",
            containerMinWidth:'0%',
        }

        this.closeNav = this.closeNav.bind(this);
        this.openNav = this.openNav.bind(this);
        this.toggleNav = this.toggleNav.bind(this);
    }
    
    render() {
        return (
            <div>
                <a onClick={this.toggleNav}>See Menu</a>
                <div id={'sidenavContainer'} className={'sidenav'} > 
                    <a className="closebtn" onClick={this.closeNav}>&times;</a>
                    <Link to='/login'>Login for Users</Link>
                    <Link to='/register'>Registration</Link>
                    <Link to='/search'>Search</Link>
                </div>
            </div>
        );
    }
    
    toggleNav() {
        console.log("sidenav toggled");
        if(this.state.containerOpened) {
            this.closeNav();
        } else {
            this.openNav();
        }

    }

    closeNav() {
        console.log("sidenav closed");
        document.getElementById('sidenavContainer').style.width = this.state.containerMinWidth;
        this.setState({containerOpened : false})
    }
    
    openNav() {
        console.log("nav opened");
        document.getElementById('sidenavContainer').style.width = this.state.containerMaxWidth;
        this.setState({containerOpened : true})
    }
}

export default SideMenu;

