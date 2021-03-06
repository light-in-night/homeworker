import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'

class Home extends Component{
    constructor(props){
        super(props);
        this.state = {
            STATUS : "loading",
            ERROR_MESSAGE : "",
            categories : [],
            
            searchInput : "",
        }
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.fetchCategoriesAndPosts();
    }

    fetchCategoriesAndPosts = () => {
        fetch("http://localhost/categories")
        .then(result =>  result.json() )
        .then(jsonResult => {
            this.setState(jsonResult)
        })
        .catch(error => {
            console.log(error);
        })
    }

    changeSearch = (event) => {
        this.setState({searchInput : event.target.value});
    }

    render() {
        return (
            <div>
                <div className="filterCategories">
                    <br/>
                    <b><label className={"filterLabel"}>Filter Categories : </label></b>
                    <input type='text' name={"search"} onChange={this.changeSearch} placeholder={"Search"} />
                </div>
                    {this.screen(this.state.STATUS)}
            </div>
        );
    }

    screen = (status) => {
        if(status === "OK") {
            return (<div className="category-box">
                    {this.state.categories
                        .filter(category => category.name.toLowerCase().indexOf(this.state.searchInput.toLowerCase()) > -1)
                        .map(category => 
                            <Link to={{pathname : '/posts', state : {source: 'http://localhost:80/posts?categoryId='+category.id+'', } }} 
                                style={{ textDecoration: 'none'}} key = {category.id}>
                                    <div className="category-item">
                                        <p>{category.name}</p>
                                        <p>{category.count} post{category.count <= 1 ? "" : "s"}</p>
                                    </div>
                            </Link> 
                        )}
                </div>)
        } else if(status === "ERROR") {
            return <b><label>Server-side error has occurred. Please retry again later.</label></b>
        } else if(status === "loading") {
            return <b><label>Still loading, please wait</label></b>
        }
    }   
}
export default Home