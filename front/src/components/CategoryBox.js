import React, {Component} from 'react'
import CategoryItem from "./CategoryItem";
import '../App.css';

class CategoryBox extends Component {
    constructor(props){
        super(props);
        this.state = {
            categories: this.props.categories
        };
    }

    render() {
       // console.log(this.state.categories);
        return (
            <div className="category-box">
                {this.state.categories.map((category) => {
                    console.log("a")
                  return <CategoryItem category={category} />;
                })}
            </div>
        );
    }
}

export default CategoryBox;