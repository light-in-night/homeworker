import React, {Component} from 'react'
import { BrowserRouter, Route} from 'react-router-dom'
import './App.css'
import Home from './components/Home'
import Header from './components/Header'
import Contact from './components/Contact'
import About from './components/About'
import Registration from './components/Registration';

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
            },
            {
                name: 'Category 3',
                numberOfPosts: '412'
            },
            {
                name: 'Category 4',
                numberOfPosts: '331'
            }
            ]
    };

    render() {
        return (
            <BrowserRouter>
                <div className="App">
                    <Header/>
                    <Route exact path='/' component={Home}/>
                    <Route path='/home' component={Home}/>
                    <Route path='/about' component={About}/>
                    <Route path='/contact' component={Contact}/>
                    <Route path='/register' component={Registration}/>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;
