import React, { Component } from 'react';
import '../App.css';
import CategoryBox from './CategoryBox'
import CB from './CB'
class Home extends Component{
    constructor(props){
        super(props);
        this.state = {
            items : [],
            isLoaded : false
        }
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.fetchCategoriesAndPosts();
    }

    fetchCategoriesAndPosts = () => {
        fetch("http://localhost/getpost/countbycategory")
        .then(result =>  result.json() )
        .then((jsonResult) => {
            this.setState({items : jsonResult.data, isLoaded : true})
            console.log(this.state.items);
        })
        .catch(error => {
            console.log(error);
        })
    }

    render() {
        console.log(this.state.items);
        // if(this.state.isLoaded) {
        //     return (
        //         <div className="App">
        //             <CategoryBox categories={this.state.items}/>
        //         </div>
        //     );
        // } else {
        //     return (<div className="App">
        //         <h1>Still loading...</h1>
        //     </div>);
        // }
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