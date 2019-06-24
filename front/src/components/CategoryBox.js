import React, {Component} from 'react'
import CategoryItem from "./CategoryItem";
import '../App.css';

class CategoryBox extends Component {

    render() {
        var categories = this.props.items;
        return (
            <div className="category-box" >
                {categories.map((category) => {
                  return <CategoryItem category={category} />;
                })}
            </div>
        );
    }
}

export default CategoryBox;