import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import Form from 'react-bootstrap/Form'
import '../App.css';
import App from '../App';

class PostCreation extends Component{

    constructor(props) {
        super(props);
        
        this.state =  {
            userId : 1,         //TODO: change this
            contents : "",
            categories : [1] , 
            allCategories:[] ,
        };
    }

    componentDidMount() {
        
        this.getAllCategories();
    }

    getAllCategories = () => {
        fetch('http://localhost:80/categories')
        .then(response => response.json())
        .then(response => {
            console.log("current state is " + this.state);
            console.log("response is " + response);
            this.setState({allCategories : response.categories})
        })
        .catch(e => console.log(e))
    }

    makePost = (e) => {
        e.preventDefault();
        let request = JSON.stringify(this.state);
        App.getUserSessionId((sessionId) => {
            console.log(sessionId);
            fetch('http://localhost:80/hasSession/isLoggedIn/posts', {
                method: 'POST',
                headers: { 'sessionId': sessionId },
                body: request
            }).then((response) => {
                response.json()
                .then((data) => {
                    console.log(data);
                })
            })
        })
    };

    handleContentChange = (e) => {
        this.setState({contents: e.target.value});
    };
    oncl = (e) => {
        console.log("aa");
        this.setState({categories:[e.target.value]})
        console.log(e.target.value)
    };

    render() {
        return (
            <div>
            
            <form onSubmit={this.makePost.bind(this)}>
                <label>write post</label>
                <textarea rows="10" cols="50" onChange={this.handleContentChange.bind(this)} required /><br/>
                <label>select categories :</label>
                <select name="cars" onChange={this.oncl.bind(this)}>
                    {
                        this.state.allCategories.map((category) => 
                                <option  value={category.id} >{category.name}</option>
                        )
                    }
                        
                </select>

                <input type="submit" value="Submit" />


            </form>
            </div>  
        );
    }
}

export default PostCreation