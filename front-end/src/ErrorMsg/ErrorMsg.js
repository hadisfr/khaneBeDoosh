import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './ErrorMsg.css'

const err_msg = {
    "403": "دسترسی مجاز نیست",
    "404": "صفحه پیدا نشد",
    "500": "سرور مشکل پیدا کرده است",
};

class ErrorMsg extends Component {
    render() {
        return (
            <div className="error-msg">
                <h3>خطای {this.props.match.params.id}</h3>
                <h4>{err_msg[this.props.match.params.id] ? err_msg[this.props.match.params.id] + "." : ""}</h4>
            </div>
        );
    }
}

export default withRouter(ErrorMsg);
