import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Pay.css'
import PayForm from './PayForm'

class Pay extends Component {
    render() {
        return (
            <div className="row pay">
                <div className="col-12 col-lg-6 current-credit">
                    <span className="gray">اعتبار کنونی </span> {this.props.user.balance} <span className="gray"> تومان</span>
                </div>
                <div className="col-12 col-lg-6"><PayForm msgPresenter={this.props.msgPresenter} /></div>
            </div>
        );
    }
}

export default withRouter(Pay);
