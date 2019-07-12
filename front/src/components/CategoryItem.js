import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../App.css';

class CategoryItem extends Component {
    constructor(props){
        super(props);
        this.state = {
            category : this.props.category
        };
    }

    render() {
        return (
            <div>
            <Link to={{pathname : '/posts', state : {source: `http://localhost:80/getpost/bycategory?categoryId=${this.state.category.categoryId}`, } }} 
                style={{ textDecoration: 'none'}}>
                <div className="category-item">
                    <p>{this.state.category.categoryName}</p>
                    <p>{this.state.category.postCount} post{this.state.category.postCount !== 1 ? "s" : ""}</p>
                </div>
            </Link>
            </div>
        );
    }
}

export default CategoryItem;