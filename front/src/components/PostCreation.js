import React, {Component} from 'react';
import '../App.css';
import App from '../App';

class PostCreation extends Component{

    constructor(props) {
        super(props);
        
        this.state =  {
            userId : 1,         //TODO: change this
            
            post : {contents : ""},
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
        let request = JSON.stringify(this.state);
        App.getUserSessionId( (sessionId) =>
            fetch('http://localhost/hasSession/isLoggedIn/posts', {
                method: 'POST',
                headers: { 'sessionId': sessionId },
                body: request
            }).then((response) => {
                response.json().then((data) => {
                    if (data.STATUS === 'OK') {
                        console.log('post added');
                    } else {
                        console.log('you are not logged in ' + data.ERROR_MESSAGE);
                        window.location.replace("/login");
                    }
                })
            }))
    };

    handleContentChange = (e) => {
        this.setState({post:{contents: e.target.value}});
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
                <b><label>write post</label></b><br></br>
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