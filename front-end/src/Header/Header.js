import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import './Header.css'
import Logo from './Logo'
import HoverMenu from './HoverMenu'

class Header extends Component {
    render() {
        return (
            <div className="header row">
                <div className="col-sm-12 col-lg-2">
                    <Logo img="pics/logo.svg" title="خانه‌به‌دوش" />
                </div>
                <div className="col-sm-0 col-lg-8 vanish-sm"></div>
                <div className="col-sm-12 col-lg-2">
                    <HoverMenu user={this.props.user} />
                </div>
            </div>
        );
    }
}

export default withRouter(Header);
