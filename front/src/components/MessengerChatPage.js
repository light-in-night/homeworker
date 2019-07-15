import React, {Component} from 'react'
import App from "../App";
import {array} from "prop-types";
import CategoryItem from "./CategoryItem";

class MessengerChatPage extends Component {

    constructor(props) {
        super(props);
        let sessionId = null;
        this.state = {
            socket: null,
            hostUser: null,
            targetUser: null,
            messages: null
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
        this.sendMessage = this.sendMessage.bind(this);
        this.receiveMessage = this.receiveMessage.bind(this);
        this.getMessages = this.getMessages.bind(this);
    }

    updateState(hostUser, sessionId) {
    if (hostUser === null || hostUser === undefined || hostUser.id === null || hostUser.id === undefined) {
            window.location.replace('/login');
        }
        let targetUser = null;
       console.log(this.props.match.params.id);
       fetch('http://localhost/users/userAccess?id='+this.props.match.params.id , {method:'GET'})
        .then( (response) => {
            response.json().then((data)=>{ 
            console.log(data);
            targetUser=data.users[0];
            console.log(this.state) ;
            console.log(hostUser);
        console.log(targetUser);
        this.setState({
            hostUser: targetUser,
            targetUser: hostUser,
            socket: new WebSocket('ws://localhost/chat/'+hostUser.id+'!'+targetUser.id+'!'+sessionId),
            messages: []
        }, () => this.state.socket.addEventListener('message', this.receiveMessage)); 
            })
        })
        
    }

    receiveMessage(event) {
        let incomingMessage = JSON.parse(event.data);
        let messagesSet = this.state.messages;
        if (incomingMessage.constructor === [].constructor) {
            incomingMessage.forEach((message) => {
                this.setState(() => messagesSet.push(message));
            })
        } else {
            this.setState(() => messagesSet.push(incomingMessage));
        }
        console.log(this.state.messages);
    }

    sendMessage(event) {
        event.preventDefault();
        let message = {
            senderId: this.state.hostUser.id,
            receiverId: this.state.targetUser.id,
            message: document.getElementById('message').value,
            sendTime: new Date()
        };
        message = JSON.stringify(message);
        this.state.socket.send(message);
        document.getElementById('message').value = '';
    }

    getMessages() {
        return this.state.messages;
    }

    formStyle = {
      padding: '70px',
      margin: '0 auto'
    };

    containerStyle = {
        display: 'flex',
        flexDirection: 'column'
    };

    senderStyle = {
        color: 'white',
        marginLeft: 'auto',
        padding: '10px',
        backgroundColor: 'blue',
        borderRadius: '25px',
        marginBottom: '5px'
    };

    receiverStyle =  {
        color: 'white',
        marginRight: 'auto',
        padding: '10px',
        backgroundColor: 'green',
        borderRadius: '25px',
        marginBottom: '5px'
    };

    headerStyle = {
        position: 'fixed',
        zIndex: '100',
        textAlign: 'center',
        width: '100%'
    };

    headerTextStyle = {
        color: 'blue',
        fontWeight: 'bold',
        fontSize: '1.4em'
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        window.scrollTo(0,document.body.scrollHeight);
    }

    render() {
        let messages = this.state.messages;
        let hostUser = this.state.hostUser;
        return (
        <div style={this.containerStyle}>
            <div style={this.headerStyle}><p style={this.headerTextStyle}>{hostUser != null ? hostUser.firstName + ' ' + hostUser.lastName : 'Hello'}</p></div>
            {messages != null ? messages.map((elem) => <div style={elem.senderId == this.state.hostUser.id ? this.senderStyle : this.receiverStyle}>{elem.message}</div>) : 'wait'}
            <form method='POST' onSubmit={this.sendMessage} style={this.formStyle}>
                <input type='text' name='Message' id='message'/> <br></br>
                <input type='submit' className='createPostButton' name='Send' value='send'/>
            </form>
        </div>
        );
    }
}

export default MessengerChatPage;