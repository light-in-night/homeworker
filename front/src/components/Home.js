import React, { Component } from 'react';
import '../App.css';
import CategoryBox from './CategoryBox'

class Home extends Component{

    categories = () => {
        return [
            {
                name: 'Homemade',
                numberOfPosts: '500'
            },
            {
                name: 'Vanilla',
                numberOfPosts: '500'
            },
            {
                name: 'MILF',
                numberOfPosts: '231'
            },
            {
                name: 'Hentai',
                numberOfPosts: '11512'
            }
            ]
    };

    render() {
        return (
            <div className="App">
                <CategoryBox categories={this.categories()}/>
            </div>
        );
    }
}

export default Home