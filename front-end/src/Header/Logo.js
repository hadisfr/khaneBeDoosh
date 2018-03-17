import React, { Component } from 'react';
import { Route, withRouter, Link } from 'react-router-dom'
import './Header.css'

class Logo extends Component {
    render() {
        return (
            <Link className="logo" to="/">
                <img className="logo" src={this.props.img} alt={this.props.title} />&nbsp;
                <span className="logo">{this.props.title}</span>
            </Link>
        );
    }
}

export default withRouter(Logo);
