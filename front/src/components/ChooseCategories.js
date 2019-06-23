import React, {Component} from 'react'
import {ButtonGroup, ToggleButton} from 'react-bootstrap';
import CategoryItem from "./CategoryItem";
import '../App.css';

class ChooseCategories extends Component {
    constructor(props){
        super(props);
        this.state = {
            categories: this.props.location.state.categories,
            selectedCategories: []
        };
    }

    addCategoryToSelected = (event, category) => {
        event.target.classList = "category-item-selected"
        this.setState({selectedCategories : this.state.selectedCategories.concat([category])})
        console.log(this.state.selectedCategories);
    }

    render() {
        return (
            <div classname="category-box">
                <div>
                {this.state.categories.map((category) => {
                   return (
                    <div className="category-item" onClick={(e) => this.addCategoryToSelected(e, category)}>
                        <p>{category.categoryName}</p>
                        <p>{category.description}}</p>
                    </div>
                   )
                })}
                </div>
            </div>
        );
    }
}

export default ChooseCategories;