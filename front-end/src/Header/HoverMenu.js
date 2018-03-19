import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom'
import './Header.css'
import frontend_api from '../front-end-api.json'

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
                        <div>{this.props.user ? this.props.user.name : "کاربر مهمان"}</div>
                        {this.props.user &&
                            <div className="unimportant">
                                <span className="center-right w50">اعتبار:</span>
                                <span className="center-left w50">{this.props.user.balance} تومان</span>
                            </div>
                        }
                        {!(this.props.user) &&
                            <div><Link to={frontend_api.root}><button className="btn btn-red">ورود به سایت</button></Link></div>
                        }
                        {this.props.user &&
                            <div><Link to={frontend_api.pay}><button className="btn btn-green">افزایش اعتبار</button></Link></div>
                        }
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(HoverMenu);
