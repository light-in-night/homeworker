import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../App.css';
import SideMenu from './SideMenu';
import About from './About'

class Header extends Component {

    render() {
        return (
            <header>

                <div className={'container'}>

                    <h1 className={'brand'}>Homeworker</h1>
                    <nav>
                        <ul>
                            <li><SideMenu/></li>
                            <li><Link to='/home'>Home</Link></li>
                            <li><Link to='/contact'>Contact</Link></li>
                            <li><Link to='/about'>About</Link></li>
                        </ul>
                    </nav>
                </div>
            </header>
        );
    }

    
}

export default Header;

