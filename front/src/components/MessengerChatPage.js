import React, {Component} from 'react'
import App from "../App";

class MessengerChatPage extends Component {

    constructor(props) {
        super(props);
        let sessionId = null;
        this.state = {
            socket: null,
            hostUser: null,
            targetUser: null
        };
        App.getUserSessionId((id) => {
            sessionId = id;
            fetch('http://localhost/hasSession/isLoggedIn/privateUserInfo', {
                method: 'GET',
                headers: {
                    'sessionId': id
                }
            }).then((result) => result.json()
                .then((hostUser) => {
                    this.updateState(hostUser, sessionId);
                }));
        });
        this.updateState = this.updateState.bind(this);
    }

    updateState(hostUser, sessionId) {
    if (hostUser === null || hostUser === undefined || hostUser.id === null || hostUser.id === undefined) {
            window.location.replace('/login');
        }
        const targetUser = this.props.user;
        this.setState({
            hostUser: targetUser,
            targetUser: hostUser,
            socket: new WebSocket('ws://localhost/chat/'+hostUser.id+'!'+targetUser.id+'!'+sessionId)
        });
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        console.log(nextState);
        return true;
    }

    static updateMessages(event) {
        console.log(event);
    }

    sendMessage(event) {
        // event.preventDefault();
        // this.state.socket.send('hello');
    }

    render() {
        return (
            <div>
                <p>Hello world!</p>
                <form onSubmit={this.sendMessage}>
                    <input id='message' type='text' name='message'/>
                    <input type='submit'/>
                </form>
            </div>
        );
    }
}

export default MessengerChatPage;