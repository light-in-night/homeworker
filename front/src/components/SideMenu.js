import React, { Component } from 'react';
import {  Link } from 'react-router-dom';
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
                <a href="#" onClick={this.toggleNav}>See Menu</a>
                <div id={'sidenavContainer'} className={'sidenav'} > 
                    <a href="#" className="closebtn" onClick={this.closeNav}>&times;</a>
                    <Link to='/login' onClick={this.closeNav}>Login for Users</Link>
                    <Link to='/register'onClick={this.closeNav}>Registration</Link>
                    <Link to='/search'onClick={this.closeNav}>Search</Link>
                    <Link to='/createPost'onClick={this.closeNav}>Create Post</Link>
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

