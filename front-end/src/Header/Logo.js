import React, {Component} from 'react';
import {withRouter, Link} from 'react-router-dom';
import './Header.css';
import frontend_api from '../front-end-api.json';

class Logo extends Component {
    render() {
        return (
            <Link className='logo' to={frontend_api.root}>
                <img className='logo' src={this.props.img} alt={this.props.title}/>&nbsp;
                <span className='logo'>{this.props.title}</span>
            </Link>
        );
    }
}

export default withRouter(Logo);
