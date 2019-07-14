import React, {Component} from 'react'

class Messenger extends Component {

    constructor(props) {
        super(props);
        this.state = {
            socket: new WebSocket('ws://localhost/chat/gurama')
        };
        this.state.socket.onmessage = ((e) => Messenger.updateMessages(e));
        this.sendMessage = this.sendMessage.bind(this);
    }

    static updateMessages(event) {
        console.log(event);
    }

    sendMessage(event) {
        event.preventDefault();
        this.state.socket.send('hello');
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

export default Messenger;