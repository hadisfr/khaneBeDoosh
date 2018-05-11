import React, { Component } from 'react';
import { Redirect, withRouter } from 'react-router-dom';
import './ErrorMsg.css';
import frontend_api from '../front-end-api.json';

const err_msg = {
    '400': 'درخواست اشتباه است',
    '401': 'ابتدا باید احراز هویت شوید',
    '402': 'پرداخت لازم است',
    '403': 'دسترسی مجاز نیست',
    '404': 'صفحه پیدا نشد',
    '500': 'سرور مشکل پیدا کرده است',
    '501': 'پیاده‌سازی نشده است',
    '502': 'دروازه مشکل دارد',
};

class ErrorMsg extends Component {
    render() {
        if (this.props.match.params.id == 401) {
            return (
                <Redirect to={frontend_api.login} />
            );
        }
        return (
            <div className='error-msg'>
                <h3>خطای {this.props.match.params.id}</h3>
                <h4>{err_msg[this.props.match.params.id] ? err_msg[this.props.match.params.id] + '.' : ''}</h4>
            </div>
        );
    }
}

export default withRouter(ErrorMsg);
