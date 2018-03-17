import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import HttpStatus from 'http-status-codes';
import 'whatwg-fetch'
import './Pay.css'

class PayForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            balance: "",
        };
    }

    handleChange(event) {
        event.preventDefault();
        this.setState({balance: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        fetch(this.props.api, {
            method: "POST",
            body: new URLSearchParams("balance=" + this.state.balance),
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
        }).then(function (res) {
            if(res.status == HttpStatus.OK) {
                this.props.msgPresenter.showMsg("اعتبار شما افزایش یافت!");
                this.props.history.goBack();
            }
            else {
                this.props.history.push("/err/" + res.status);
            }
        }.bind(this));
    }

    render() {
        return (
            <form onSubmit={(event) => this.handleSubmit(event)}>
                <input
                    type="number"
                    name="balance"
                    pattern="[0-9]+"
                    placeholder="مبلغ مورد نظر"
                    onChange={(event) => this.handleChange(event)}
                    value={this.state.balance}
                />
                <span className="badge">تومان</span>
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
