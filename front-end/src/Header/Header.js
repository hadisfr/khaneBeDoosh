import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import './Header.css'

class Header extends Component {
    render() {
        return (
            <div className="header row">
                <div className="col-sm-12 col-lg-2">
                    <a className="logo" href="index.html">
                        <img className="logo" src="pics/logo.svg" alt="khaneBeDoosh" />&nbsp;
                        <span className="logo">خانه‌به‌دوش</span>
                    </a>
                </div>
                <div className="col-sm-0 col-lg-8 vanish-sm"></div>
                <div className="col-sm-12 col-lg-2 toggle">
                    <button className="btn btn-outline-violet toggle">
                        <span className="far fa-smile fa-lg"></span>&nbsp;
                        ناحیهٔ کاربری
                    </button>
                    <div className="hover-menu-wrapper">
                        <div className="hover-menu">
                            <div>بهنام همایون</div>
                            <div className="unimportant">
                                <span className="center-align w50">اعتبار:</span><span className="center-align w50">۲۰٬۰۰۰ تومان</span>
                            </div>
                            <div><button className="btn btn-green">افزایش اعتبار</button></div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(Header);
