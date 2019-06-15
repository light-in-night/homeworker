import React, {Component} from 'react'
import Header from './components/Header'
import CategoryBox from './components/CategoryBox'
import './App.css'

class App extends Component {

    categories = () => {
        return [
            {
                name: 'Category 1',
                numberOfPosts: '500'
            },
            {
                name: 'Category 2',
                numberOfPosts: '500'
            }
            ]
    };

    render() {
        return (
            <div className="App">
                <Header/>
                <CategoryBox categories={this.categories()}/>
            </div>
        );
    }
}

export default App;
