import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom'
import './Header.css'

class HoverMenu extends Component {
    render() {
        return (
            <div className="toggle">
                <button className="btn btn-outline-violet toggle">
                    <span className="far fa-smile fa-lg"></span>&nbsp;
                    ناحیهٔ کاربری
                </button>
                <div className="hover-menu-wrapper">
                    <div className="hover-menu">
                        <div>{this.props.user.username}</div>
                        <div className="unimportant">
                            <span className="center-right w50">اعتبار:</span>
                            <span className="center-left w50">{this.props.user.balance} تومان</span>
                        </div>
                        <div><Link to="/pay"><button className="btn btn-green">افزایش اعتبار</button></Link></div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(HoverMenu);
