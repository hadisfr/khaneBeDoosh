import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Landing.css'

class Card extends Component {
    render() {
        return (
            <div className='col-lg-4 col-12 box-wrapper'><div className='box-light'>
                <img src={this.props.img} alt={this.props.title} />
                <h3>{this.props.title}</h3>
                <p>{this.props.detail}</p>
            </div></div>
        );
    }
}

export default withRouter(Card);
