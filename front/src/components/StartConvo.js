import React, {Component} from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import MessengerChatPage from './MessengerChatPage'

import { IoIosMail } from 'react-icons/io';

class StartConvo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users:[]
        }
    }

    componentDidMount(){
        fetch('http://localhost/users/userAccess' , {method:'GET'})
        .then( (response) => {
            response.json().then((data)=>{ 
            console.log(data);
            this.setState(data);
            console.log(this.state)  
            })
        })
    }
    render() {
        return (
            <div>
                {this.state.users.map(user => 
                            <div id="sticky2">
                            <form className={"userInfo"}>
                                <ul className={"userInfoList"}>
                                    <li>
                                        <p style={this.textStyle}>Name: {user.firstName}</p>
                                    </li>
                                    <li>
                                        <p style={this.textStyle}>LastName: {user.lastName}</p>
                                    </li>
                                    <li>
                                        <p style={this.textStyle}><IoIosMail />Mail: {user.email}</p>
                                    </li>
                                    <li>
                                    <div>
                          <Link to={{pathname : '/asd/'+this.state.id }} 
                                       >send message</Link>
                               </div>  
                                    </li>
                                </ul>
    
                            </form>
                        </div>     
                    )
                }
            </div>
        );
    }

}
export default StartConvo;