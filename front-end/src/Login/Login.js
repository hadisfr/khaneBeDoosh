import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import HttpStatus from 'http-status-codes';
import './Login.css';
import backend_api from '../back-end-api.json';
import frontend_api from '../front-end-api.json';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };
    }

    handleChange(event) {
        event.preventDefault();
        var newState = {};
        newState[event.target.name] = event.target.value;
        this.setState(newState);
    }

    handleSubmit(event) {
        event.preventDefault();
        const query = Object.keys(this.state).filter((key) => (this.state[key]))
            .map((key) => (key + '=' + this.state[key])).join('&');
        fetch(backend_api.login + '?' + query)
            .then(
                (res) => (res.status === HttpStatus.OK ? res.json() : this.props.history.push(frontend_api.error + res.status)),
                (err) => (this.props.msgPresenter.showMsg('ورود ناموفق بود.'))
            ).then(function (res) {
            this.props.msgPresenter.showMsg('خوش آمدید!');
            window.localStorage.setItem("token", res.token);
            this.props.callBack();
            this.props.history.goBack();
        }.bind(this), (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    render() {
        return (
            <div className='row login'>
                <div className='col-0 col-lg-4'>&nbsp;</div>
                <div className='col-12 col-lg-4'>
                    <form onSubmit={(event) => this.handleSubmit(event)}>
                        <input
                            type='text'
                            name='username'
                            dir='ltr'
                            placeholder='نام کاربری'
                            onChange={(event) => this.handleChange(event)}
                            value={this.state.username}
                        />
                        <input
                            type='password'
                            name='password'
                            placeholder='گذرواژه'
                            onChange={(event) => this.handleChange(event)}
                            value={this.state.password}
                        />
                        <input
                            type='submit'
                            value='ورود'
                            className='btn btn-green'
                            required
                            disabled={!(RegExp('^[0123456789]+$').test(0))}
                        />
                    </form>
                </div>
                <div className='col-0 col-lg-4'>&nbsp;</div>
            </div>
        );
    }
}

export default withRouter(Login);
