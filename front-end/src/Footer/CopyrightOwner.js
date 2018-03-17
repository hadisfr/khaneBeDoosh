import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Footer.css';

class CopyrightOwner extends Component {
    render() {
        return (
            <a 
                href={this.props.website} target="_blank"
                title={this.props.name + " :: " + this.props.website}
                className="copyright-owner"
            >
                {this.props.name}
            </a>
        );
    }
}

export default withRouter(CopyrightOwner);
