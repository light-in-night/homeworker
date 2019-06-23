import React, { Component } from 'react';
import '../App.css';

class CategoryItem extends Component {


    render() {
        var name= this.props.category.name ;
        var numberOfPosts= this.props.category.numberOfPosts ;
        return (
            <div className="category-item" >
                <p>{name}</p>
                <p>{numberOfPosts}</p>
            </div>
        );
    }
}

export default CategoryItem;