
import React, {Component} from 'react'

import '../App.css';
class PersonInfo extends Component {

    render() {
        return (
            <div className="PersonInfo" >
                <h1 className="hit-the-floor ">{this.props.person}</h1><h3 className="drop-the-floor">{this.props.text}</h3>
            </div>
        );
    }
}

export default PersonInfo;
