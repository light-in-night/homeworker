import React, { Component } from 'react';
import '../App.css';

class Header extends Component {
    render() {
        return (
            <header>

                <div className={'container'}>

                    <h1 className={'brand'}>Homeworker</h1>
                    <nav>
                        <ul>
                            <li><a href={'#'}>Home</a></li>
                            <li><a href={'#'}>User</a></li>
                        </ul>
                    </nav>

                </div>

            </header>
        );
    }
}

export default Header;

