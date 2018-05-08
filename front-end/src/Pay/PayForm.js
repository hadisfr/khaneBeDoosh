import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import HttpStatus from 'http-status-codes';
import './Pay.css'
import backend_api from '../back-end-api.json'
import frontend_api from '../front-end-api.json'

class PayForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            balance: '',
        };
    }

    handleChange(event) {
        event.preventDefault();
        this.setState({balance: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        fetch(backend_api.pay, {
            method: 'POST',
            body: new URLSearchParams('balance=' + this.state.balance),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        }).then(
            function (res) {
                if(res.status === HttpStatus.OK) {
                    this.props.msgPresenter.showMsg('اعتبار شما افزایش یافت!');
                    this.props.history.goBack();
                }
                else {
                    this.props.history.push(frontend_api.error + res.status);
                }
                this.props.callBack();
            }.bind(this),
                (err) => (
                    this.props.msgPresenter.showMsg(String(err))
                    || this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)
                )
            );
    }

    render() {
        return (
            <form onSubmit={(event) => this.handleSubmit(event)}>
                <input
                    type='number'
                    name='balance'
                    min='0'
                    step='1'
                    placeholder='مبلغ مورد نظر'
                    onChange={(event) => this.handleChange(event)}
                    value={this.state.balance}
                />
                <span className='pay-badge'>تومان</span>
                <input
                    type='submit'
                    value='افزایش اعتبار'
                    className='btn btn-green'
                    required
                    disabled={!(RegExp('^[0123456789]+$').test(this.state.balance))}
                />
            </form>
        );
    }
}

export default withRouter(PayForm);
