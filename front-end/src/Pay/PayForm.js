import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Pay.css'

class PayForm extends Component {
    render() {
        return (
            <form>
                <input type="number" name="balance" pattern="[0-9]+" placeholder="مبلغ مورد نظر" />
                <span className="badge">تومان</span>
                <input type="submit" value="افزایش اعتبار" className="btn btn-green" />
            </form>
        );
    }
}

export default withRouter(PayForm);
