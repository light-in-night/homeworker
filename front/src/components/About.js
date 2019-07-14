import React, { Component } from 'react';
import '../App.css';

import PersonInfo from './PersonInfo'
class About extends Component{
    constructor(props) {
        super(props);
        this.handleMouseHover1 = this.handleMouseHover1.bind(this);
        this.handleMouseHover2 = this.handleMouseHover2.bind(this);
        this.handleMouseHover3 = this.handleMouseHover3.bind(this);
        this.handleMouseHover4 = this.handleMouseHover4.bind(this);
        this.handleMouseHover5 = this.handleMouseHover5.bind(this);
        this.state = {
          isHovering1: false,
          isHovering2: false,
          isHovering3: false,
          isHovering4: false,
          isHovering5:false
        };
      }
    
      handleMouseHover1() {
        this.setState(this.toggleHover1State);
      }
      handleMouseHover2() {
        this.setState(this.toggleHover2State);
      }
      handleMouseHover3() {
        this.setState(this.toggleHover3State);
      }
      handleMouseHover4() {
        this.setState(this.toggleHover4State);
      }
      handleMouseHover5() {
        this.setState(this.toggleHover5State);
      }
    
      toggleHover1State(state) {
        return {
          isHovering1: !state.isHovering1,
        };
      }
      toggleHover2State(state) {
        return {
          isHovering2: !state.isHovering2,
        };
      }
      toggleHover3State(state) {
        return {
          isHovering3: !state.isHovering3,
        };
      }
      toggleHover4State(state) {
        return {
          isHovering4: !state.isHovering4,
        };
      }
      toggleHover5State(state) {
        return {
          isHovering5: !state.isHovering5,
        };
      }
      
    render() {    
        return (
          <div className="aboutCss">
              <div>
              <h2>ABOUT HOMEWORKER CREATORS</h2>
              <p>
                  We are super puper cool dudes who did this website for uni project
              </p>
              <div className="aboutClass">
              <div className="AboutClassItem"
          onMouseEnter={this.handleMouseHover1}
          onMouseLeave={this.handleMouseHover1}
        
        >
             <img src="tornike.jpg" width={130} height={140} alt = "asd" />
            
          
        </div>
        <div className="AboutClassItem"
          onMouseEnter={this.handleMouseHover2}
          onMouseLeave={this.handleMouseHover2}
        >
             <img src="koka.jpg" width={130} height={140} alt = "asd" />
         
        </div>
        <div className="AboutClassItem"
          onMouseEnter={this.handleMouseHover3}
          onMouseLeave={this.handleMouseHover3}
        >
             <img src="ono.jpg" width={130} height={140} alt = "asd" />
         
        </div>
        <div className="AboutClassItem"
          onMouseEnter={this.handleMouseHover4}
          onMouseLeave={this.handleMouseHover4}
        >
             <img src="tyesh.jpg" width={130} height={140} alt = "asd" />
          
        </div >
        <div className="AboutClassItem"
          onMouseEnter={this.handleMouseHover5}
          onMouseLeave={this.handleMouseHover5}
        >
            <img src="givia.jpg" width={130} height={140} alt = "asd" />
         
        </div>
              

              </div>
              {
          this.state.isHovering1 &&
          <div>
          <PersonInfo text="Tornike is a hard working young man whose code is even sexier than his body" person="Tornike Kechackmadze" /> 
          </div>
        }
        {
          this.state.isHovering2 &&
          <div>
          <PersonInfo text="David is a high level professional" person="Dato Kokaia" />
          </div>
        }
        {
          this.state.isHovering3 &&
          <div>
            <PersonInfo text="Tornike is a hard-working man , will write his code even from Italy.also known as BEAST of injineria" person="Tornike Onoprishvili" />
          </div>
        }
        {
          this.state.isHovering4 &&
          <div>
           <PersonInfo text="Humble human being and a great programmer , we are lucky to have such an extraordinary coder in our team.He will always be my  software architect" person="Guga Tkesheladze" />
          </div>
        }
        {
          this.state.isHovering5 &&
          <div>
           <PersonInfo text="i am... i mean givi is an ACE of homeworker , he worked hard day and night for this project to succeed " person="Givi Khartishvili" />
          </div>
        }

          </div>  
          </div>
        );
    }
   
    
}

export default About