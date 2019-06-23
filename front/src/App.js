import React, {Component} from 'react'
import { BrowserRouter, Route} from 'react-router-dom'
import './App.css'
import Home from './components/Home'
import Header from './components/Header'
import Contact from './components/Contact'
import About from './components/About'
import Registration from './components/Registration';
import PostCreation from './components/PostCreation'
import Login from './components/Login'
import ExpandingPostWall from './components/ExpandingPostWall'
import ChooseCategories from './components/ChooseCategories'

class App extends Component {

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
                    <Route path='/createPost' component={PostCreation}/>
                    <Route path='/login' component={Login}/>
                    <Route path='/posts' component={ExpandingPostWall}/>
                    <Route path='/chooseCategories' component={ChooseCategories}/>
                </div>
            </BrowserRouter>
           
        );
    }
}

export default App;
