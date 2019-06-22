import React, { Component } from 'react';
import '../App.css';
import CategoryBox from './CategoryBox'

class Home extends Component{
constructor(){
    super();
    this.state={
      search: "",
      categories:this.categories()
    };
}
    categories = () => {
        return [

            {
                name: 'Stepmom',
                numberOfPosts: '100'
            },
            {
                name: 'Homemade',
                numberOfPosts: '500'
            },
            {
                name: 'Vanilla',
                numberOfPosts: '500'
            },
            {
                name: 'MILF',
                numberOfPosts: '231'
            },
            {
                name: 'Hentai',
                numberOfPosts: '11512'
            },
            {
                name: 'GangBang',
                numberOfPosts: '6969'
            },
            {
                name: 'BBC',
                numberOfPosts: '2525'
            }
            ]
    };
    changeSearch = (event) =>{
        this.setState({ search:event.target.value});
    }

    render() {
        var items = this.state.categories.filter(
            (post) =>{return post.name.toLowerCase().indexOf(this.state.search.toLowerCase())!==-1});
            console.log(items);
        return (
            <div className="App">
                <b><label>Filter Posts : </label></b><input type='text' onChange={this.changeSearch.bind(this)} />
                <CategoryBox items={items}/>                                                                         
            </div>
        );
    }
}
export default Home