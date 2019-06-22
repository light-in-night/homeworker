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
            }
            ]
    };
    changeSearch = (event) =>{
        this.setState({ search:event.target.value});
    }

    render() {
        var items = this.state.categories.filter(
            (post) =>{return post.name.indexOf(this.state.search)!==-1});
            console.log(items);
        return (
            <div className="App">
                <label>Filter Posts</label><input type='text' onChange={this.changeSearch.bind(this)} />
                <CategoryBox items={items}/>                                                                         
            </div>
        );
    }
}

export default Home