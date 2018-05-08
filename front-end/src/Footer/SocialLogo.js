import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Footer.css';

class SocialLogo extends Component {
    render() {
        return (
            <a href={this.props.url} target='_blank' title={this.props.title} className='social-logo'>
                <img src={this.props.img} alt={this.props.title} />
            </a>
        );
    }
}

export default withRouter(SocialLogo);
