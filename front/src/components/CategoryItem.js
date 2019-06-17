import React, { Component } from 'react';
import '../App.css';

class CategoryItem extends Component {

    state = {
        name: this.props.category.name,
        numberOfPosts: this.props.category.numberOfPosts
    };

    render() {
        return (
            <div className="category-item" >
                <p>{this.state.name}</p>
                <p>{this.state.numberOfPosts}</p>
            </div>
        );
    }
}

export default CategoryItem;