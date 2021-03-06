import React, {Component} from 'react'
import {BrowserRouter, Route} from 'react-router-dom'
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
import CB from './components/CB'
import User from './components/User'
import cookies from 'react-cookies'
import Post from './components/Post'
import MessengerChatPage from "./components/MessengerChatPage";
import visitUser from './components/visitUser'
import chatter from './components/StartConvo'
class App extends Component {

    constructor(props) {
        super(props);
        this.state = { userSessionId: null};
    }

    static getUserSessionId(callback) {
        let userSessionId = cookies.load('userSessionId');
        if (userSessionId === undefined || userSessionId == null) {
            App.addSessionCookie();
        } else {
            fetch('http://localhost/sessions?sessionId=' + userSessionId, {method: 'GET'})
                .then((response) => response.json()
                    .then((data) => {
                        App.removeSession(data.isValid)
                    }))
        }
        callback(cookies.load('userSessionId'));
    }

    static removeSession(valid) {
        if (!valid) {
            cookies.remove('userSessionId', {path: '/'});
            App.addSessionCookie();
        }
    }

    static addSessionCookie() {
        App.getNewUserSessionId((newSessionId) => {
            cookies.save('userSessionId', newSessionId, { path: '/' });
        });
    }
    static logout(){

        App.getUserSessionId( (sessionId) =>
            fetch('http://localhost/hasSession/isLoggedIn/logout', {
                method: 'POST',
                headers: { 'sessionId': sessionId },
            }).then((response) => {
                console.log(response);
            }))
    }

    static getNewUserSessionId(callback) {
        fetch('http://localhost/sessions', {method: 'POST'})
            .then((result) => result.json()
                .then((data) => callback(data.sessionId)))
    }

    user = {
        id: '2',
        firstName: 'guga',
        lastName: 'gugashvili',
        gender: 'male'
    };

    render() {
        let user = this.user;
        return (
              <BrowserRouter logInfo={this.state}>
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
                    {/*<Route path='/messenger' component={Messenger}/> TODO immplement this*/}
                    <Route path='/messenger' render={() => <MessengerChatPage user={user} />} />
                    <Route path='/asd/:id' component={MessengerChatPage}/>
                    <Route path='/chooseCategories' component={ChooseCategories}/>
                    <Route path='/chatBot' component ={CB}/>
                    <Route path='/User' component = {User}/>
                    <Route path='/Post' component = {Post}/>
                    <Route path ='/visitUser' component = {visitUser}/>
                    <Route path ='/startConvo' component = {chatter}/>
                </div>
            </BrowserRouter>
           
        );
    }
}

export default App;
