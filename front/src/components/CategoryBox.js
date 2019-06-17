import React, {Component} from 'react'
import CategoryItem from "./CategoryItem";
import '../App.css';

class CategoryBox extends Component {

    state = {
        categories: this.props.categories
    };

    render() {
        return (
            <div className="category-box" >
                {this.state.categories.map((category) => {
                  return <CategoryItem category={category} />;
                })}
            </div>
        );
    }
}

export default CategoryBox;