import React, {Component} from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import MessengerChatPage from './MessengerChatPage'
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
                      <div>
                      <Link to={{pathname : '/asd/'+user.id }} 
                                   >{user.firstName} {user.lastName}</Link>
                           </div>        
                    )
                }
            </div>
        );
    }

}
export default StartConvo;