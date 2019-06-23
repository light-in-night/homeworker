import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import Form from 'react-bootstrap/Form'
import '../App.css';

class PostCreation extends Component{

    constructor(props) {
        super(props);
        
        this.state =  {
            userId : 1,         //TODO: change this
            contents : "",
            categories : []
        };
    }

    componentDidMount() {
        this.getAllCategories();
    }

    getAllCategories = () => {
        fetch('http://localhost:80/getcategory')
        .then(response => response.json())
        .then(response => {
            // console.log("current state is " + this.state);
            // console.log("response is " + response);
            this.setState({categories : response.categories})
        })
        .catch(e => console.log(e))
    }

    makePost = (e) => {
        e.preventDefault();
        let request = JSON.stringify(this.state);
        fetch('http://localhost:80/createpost', {
            method: 'POST',
            body: request
        }).then((response) => {
            console.log(response);
        })
    };

    handleContentChange = (e) => {
        this.setState({contents: e.target.value});
    };


    render() {
        return (
            <div>
            <Form>
                <Form.Group controlId="exampleForm.ControlTextarea1">
                    <Form.Label>Example textarea</Form.Label>
                    <Form.Control as="textarea" rows="3" />
                    <Link to={{pathname:'/chooseCategories', state: {categories : this.state.categories}}} >
                         Finish writing </Link>
                </Form.Group>
            </Form>
            </div>  
        );
    }
}

export default PostCreation