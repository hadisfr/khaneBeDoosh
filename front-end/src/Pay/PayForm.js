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
            balance: "",
        };
    }

    handle_change(event) {
        event.preventDefault();
        this.setState({balance: event.target.value});
    }

    handle_submit(event) {
        event.preventDefault();
        fetch(backend_api.pay, {
            method: "POST",
            body: new URLSearchParams("balance=" + this.state.balance),
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
        }).then(function (res) {
            if(res.status === HttpStatus.OK) {
                this.props.msgPresenter.showMsg("اعتبار شما افزایش یافت!");
                this.props.history.goBack();
            }
            else {
                this.props.history.push(frontend_api.error + res.status);
            }
        }.bind(this));
    }

    render() {
        return (
            <form onSubmit={(event) => this.handle_submit(event)}>
                <input
                    type="number"
                    name="balance"
                    pattern="[0-9]+"
                    placeholder="مبلغ مورد نظر"
                    onChange={(event) => this.handle_change(event)}
                    value={this.state.balance}
                />
                <span className="pay-badge">تومان</span>
                <input
                    type="submit"
                    value="افزایش اعتبار"
                    className="btn btn-green"
                    disabled={!(this.state.balance && this.state.balance > 0)}
                />
            </form>
        );
    }
}

export default withRouter(PayForm);
