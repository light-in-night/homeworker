import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../App.css';
import SideMenu from './SideMenu';
import App from '../App';

class Header extends Component {

    render() {
        return (
            <header>

                <div className={'container'}>

                    <Link to='/home' className={'homeLink'}>
                        <h1  className={'brand'}>Homeworker</h1>
                    </Link>
                    <nav>
                        <ul className={"menuBar"}>
                            <li><SideMenu/></li>
                            <li><Link to='/about'>About</Link></li>
                            <li><Link to='/contact'>Contact</Link></li>
                            <li><Link to='/startConvo'>CHAT</Link></li>
                            <li><Link to='/login'>Login</Link></li>
                          
                            <li><Link onClick={App.logout} to='/'>Logout</Link></li>
                        </ul>
                    </nav>

                </div>

            </header>
        );
    }

    
}

export default Header;

