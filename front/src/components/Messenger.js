import React, {Component} from 'react';
import '../App.css';
import MessengerChatPage from "./MessengerChatPage";

class Messenger extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userChatWindows: this.props.userChatWindows
        };
    }

    render() {
        return (
            <div className="messenger">
                {this.state.userChatWindows.map(user =>
                    <MessengerChatPage user={user} />
                )}
            </div>
        )
    }
}

export default Messenger;