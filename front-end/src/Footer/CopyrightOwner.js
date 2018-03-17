import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Footer.css';

class CopyrightOwner extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <a 
            href={this.props.website} target="_blank" title={this.props.name + " :: " + this.props.website} class="copyright-owner">
                {this.props.name}
            </a>
        );
    }
}

export default withRouter(CopyrightOwner);
