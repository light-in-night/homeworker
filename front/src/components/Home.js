import React, { Component } from 'react';
import '../App.css';
import CategoryBox from './CategoryBox'

class Home extends Component{
    constructor(props){
        super(props);
        this.state = {
            items : [],
            isLoaded : false
        }
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.fetchCategoriesAndPosts();
    }

    fetchCategoriesAndPosts = () => {
        fetch("http://localhost/getpost/countbycategory")
        .then((result) => {
            return result.json()
        })
        .then((jsonResult) => {
            this.setState({items : jsonResult, isLoaded : true})
            console.log(this.state.items);
        })
        .catch(error => {
            console.log(error);
        })
    }

    render() {
        console.log(this.state.items);
        if(this.state.isLoaded) {
            return (
                <div className="App">
                    <CategoryBox categories={this.state.items}/>
                </div>
            );
        } else {
            return (<div className="App">
                <h1>Still loading...</h1>
            </div>);
        }
    }
}
export default Home