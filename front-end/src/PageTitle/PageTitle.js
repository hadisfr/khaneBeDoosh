import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import './PageTitle.css'

class PageTitle extends Component {
    render() {
        return (
            <div class="title-wrapper"><h4>{this.props.title}</h4></div>
        );
    }
}

export default withRouter(PageTitle);
