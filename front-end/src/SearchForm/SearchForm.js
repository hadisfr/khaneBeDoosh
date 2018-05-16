import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './SearchForm.css';
import frontend_api from '../front-end-api.json';

class SearchForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            buildingType: '',
            maxSellPrice: '',
            maxRentPrice: '',
            maxBasePrice: '',
            minArea: '',
            dealType: ''
        };
    }

    handleChange(event) {
        if (event.target.type !== 'radio') event.preventDefault();
        this.setState({ [event.target.name]: event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
        var req = Object.assign({}, this.state);
        if (req.dealType !== 'SELL') {
            req.maxSellPrice = '';
        }
        if (req.dealType !== 'RENT') {
            req.maxRentPrice = '';
            req.maxBasePrice = '';
        }
        const query =
            '?' +
            Object.keys(req)
                .filter(key => req[key])
                .map(key => key + '=' + req[key])
                .join('&');
        this.props.history.push(frontend_api.search + query);
        if (this.props.call_back) this.props.call_back(query);
    }

    render() {
        return (
            <form onSubmit={event => this.handleSubmit(event)}>
                <div className="row search">
                    <div className="col-12 col-sm-6 col-lg-4">
                        <select
                            name="buildingType"
                            onChange={event => this.handleChange(event)}
                            value={this.state.buildingType}
                        >
                            <option value="" disabled>
                                ▼ نوع ملک
                            </option>
                            <option value="VILLA">ویلایی</option>
                            <option value="APARTMENT">آپارتمان</option>
                        </select>
                    </div>
                    <div
                        className="col-12 col-sm-6 col-lg-4"
                        style={{
                            display: this.state.dealType === 'SELL' || 'none'
                        }}
                    >
                        <input
                            type="number"
                            min="0"
                            step="1"
                            name="maxSellPrice"
                            pattern="^[0123456789]*$"
                            placeholder="حداکثر قیمت خرید"
                            value={this.state.maxSellPrice}
                            onChange={event => this.handleChange(event)}
                        />
                        <span className="badge">تومان</span>
                    </div>
                    <div
                        className="col-12 col-sm-6 col-lg-4"
                        style={{
                            display: this.state.dealType === 'RENT' || 'none'
                        }}
                    >
                        <input
                            type="number"
                            min="0"
                            step="1"
                            name="maxRentPrice"
                            pattern="^[0123456789]*$"
                            placeholder="حداکثر قیمت اجاره"
                            value={this.state.maxRentPrice}
                            onChange={event => this.handleChange(event)}
                        />
                        <span className="badge">تومان</span>
                    </div>
                    <div
                        className="col-12 col-sm-6 col-lg-4 vanish-sm"
                        style={{
                            display: this.state.dealType === '' || 'none'
                        }}
                    >
                        &nbsp;
                    </div>
                    <div className="col-12 col-sm-6 col-lg-4">
                        <input
                            type="number"
                            name="minArea"
                            min="0"
                            step="1"
                            pattern="^[0123456789]*$"
                            placeholder="حداقل مساحت"
                            value={this.state.minArea}
                            onChange={event => this.handleChange(event)}
                        />
                        <span className="badge">متر</span>
                    </div>
                    <div className="col-12 col-sm-6 col-lg-4">
                        <fieldset className="center-align">
                            <input
                                type="radio"
                                name="dealType"
                                value="SELL"
                                onChange={event => this.handleChange(event)}
                                checked={this.state.dealType === 'SELL'}
                            />خرید
                            <input
                                type="radio"
                                name="dealType"
                                value="RENT"
                                onChange={event => this.handleChange(event)}
                                checked={this.state.dealType === 'RENT'}
                            />رهن و اجاره
                        </fieldset>
                    </div>
                    <div
                        className="col-12 col-sm-6 col-lg-4"
                        style={{
                            display: this.state.dealType === 'RENT' || 'none'
                        }}
                    >
                        <input
                            type="number"
                            min="0"
                            step="1"
                            name="maxBasePrice"
                            pattern="^[0123456789]*$"
                            placeholder="حداکثر قیمت رهن"
                            value={this.state.maxBasePrice}
                            onChange={event => this.handleChange(event)}
                        />
                        <span className="badge">تومان</span>
                    </div>
                    <div
                        className="col-12 col-sm-6 col-lg-4 vanish-sm"
                        style={{
                            display: this.state.dealType !== 'RENT' || 'none'
                        }}
                    >
                        &nbsp;
                    </div>
                    <div className="col-12 col-sm-6 col-lg-4">
                        <input
                            type="submit"
                            className="btn btn-green"
                            value="جست‌وجو"
                            disabled={
                                !(
                                    RegExp('^[0123456789]*$').test(
                                        this.state.maxSellPrice
                                    ) &&
                                    RegExp('^[0123456789]*$').test(
                                        this.state.maxRentPrice
                                    ) &&
                                    RegExp('^[0123456789]*$').test(
                                        this.state.maxBasePrice
                                    ) &&
                                    RegExp('^[0123456789]*$').test(
                                        this.state.minArea
                                    )
                                )
                            }
                        />
                    </div>
                </div>
            </form>
        );
    }
}

export default withRouter(SearchForm);
