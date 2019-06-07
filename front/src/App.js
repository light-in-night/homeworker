import React from 'react'
// import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Header from './components/Header'
import RegForm from './components/RegFrom'
import './App.css'

function App() {
    return (
        <div className="App">
            <Header/>
            <RegForm/>
        </div>
    );
}

export default App;
