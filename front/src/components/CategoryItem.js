import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../App.css';

class CategoryItem extends Component {
    constructor(props){
        super(props);
        this.state = {
            id : this.props.category.categoryId,
            name: this.props.category.categoryName,
            description: this.props.category.description,
            numberOfPosts: this.props.category.postCount
        };
    }

    render() {
        var name= this.props.category.name ;
        var numberOfPosts= this.props.category.numberOfPosts ;
        return (
            <Link 
                to={{pathname : '/posts', state : {source: `http://localhost:80/getpost/bycategory?categoryId=${this.state.id}`, } }} 
                style={{ textDecoration: 'none'}}>
                <div className="category-item">
                        <p>{this.state.name}</p>
                        <p>{this.state.numberOfPosts} post{this.state.numberOfPosts !== 1 ? "s" : ""}</p>
                </div>
            </Link>
        );
    }
}

export default CategoryItem;