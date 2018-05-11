import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import './NewHouseForm.css';
import HttpStatus from 'http-status-codes';
import backend_api from '../back-end-api.json';
import frontend_api from '../front-end-api.json';

class NewHouseForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            buildingType: '',
            sellPrice: '',
            rentPrice: '',
            basePrice: '',
            area: '',
            dealType: '',
            address: '',
            phone: '',
            description: '',
        };
    }

    handleChange(event) {
        if (event.target.type !== 'radio')
            event.preventDefault();
        this.setState({[event.target.name]: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        var req = Object.assign({}, this.state);
        if (req.dealType !== 'SELL') {
            req.sellPrice = '';
        }
        if (req.dealType !== 'RENT') {
            req.rentPrice = '';
            req.basePrice = '';
        }
        const query = Object.keys(req).filter((key) => (req[key])).map((key) => (key + '=' + req[key])).join('&');
        fetch(backend_api.new_house, {
            method: 'POST',
            body: new URLSearchParams(query),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        }).then(
            function (res) {
                if (res.status === HttpStatus.OK) {
                    this.props.msgPresenter.showMsg('ملک با موفقیت ثبت شد!');
                    this.props.history.goBack();
                }
                else {
                    this.props.history.push(frontend_api.error + res.status);
                }
            }.bind(this),
            (err) => (
                this.props.msgPresenter.showMsg(String(err))
                || this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)
            )
        );
    }

    render() {
        return (
            <div className='new-house'>
                <form onSubmit={(event) => this.handleSubmit(event)}>
                    <div className='row'>
                        <div className='col-12 col-sm-6'>
                            <input
                                type='submit'
                                className='btn btn-green'
                                value='ثبت ملک'
                                disabled={!(
                                    this.state.dealType
                                    && (
                                        (
                                            this.state.dealType === 'SELL'
                                            && RegExp('^[0123456789]+$').test(this.state.sellPrice)
                                        ) || (
                                            this.state.dealType === 'RENT'
                                            && RegExp('^[0123456789]+$').test(this.state.rentPrice)
                                            && RegExp('^[0123456789]+$').test(this.state.basePrice)
                                        )
                                    )
                                    && RegExp('^[0123456789]+$').test(this.state.area)
                                    && RegExp('^0[0123456789]{10}$').test(this.state.phone)
                                    && RegExp('^0[0123456789]{10}$').test(this.state.phone)
                                    && this.state.buildingType
                                    && this.state.address
                                )}
                            />
                        </div>
                        <div className='col-12 col-sm-6'>
                            <fieldset className='center-align'>
                                <input
                                    type='radio'
                                    name='dealType'
                                    value='SELL'
                                    onChange={(event) => this.handleChange(event)}
                                    checked={this.state.dealType === 'SELL'}
                                />خرید
                                <input
                                    type='radio'
                                    name='dealType'
                                    value='RENT'
                                    onChange={(event) => this.handleChange(event)}
                                    checked={this.state.dealType === 'RENT'}
                                />رهن و اجاره
                            </fieldset>
                        </div>
                        <div className='col-12 col-sm-6'>
                            <select
                                name='buildingType'
                                onChange={(event) => this.handleChange(event)}
                                value={this.state.buildingType}
                            >
                                <option value='' disabled>▼ نوع ملک</option>
                                <option value='VILLA'>ویلایی</option>
                                <option value='APARTMENT'>آپارتمان</option>
                            </select>
                        </div>
                        <div className='col-12 col-sm-6'>
                            <input
                                type='number'
                                name='area'
                                min='0'
                                step='1'
                                pattern='^[0123456789]*$'
                                placeholder='مساحت'
                                value={this.state.area}
                                onChange={(event) => this.handleChange(event)}
                            />
                            <span className='badge'>متر</span>
                        </div>
                        <div className='col-12 col-sm-6' style={{display: this.state.dealType === 'SELL' || 'none'}}>
                            <input
                                type='number'
                                min='0'
                                step='1'
                                name='sellPrice'
                                pattern='^[0123456789]*$'
                                placeholder='قیمت خرید'
                                value={this.state.sellPrice}
                                onChange={(event) => this.handleChange(event)}
                            />
                            <span className='badge'>تومان</span>
                        </div>
                        <div className='col-12 col-sm-6' style={{display: this.state.dealType === 'RENT' || 'none'}}>
                            <input
                                type='number'
                                min='0'
                                step='1'
                                name='rentPrice'
                                pattern='^[0123456789]*$'
                                placeholder='قیمت اجاره'
                                value={this.state.rentPrice}
                                onChange={(event) => this.handleChange(event)}
                            />
                            <span className='badge'>تومان</span>
                        </div>
                        <div className='col-12 col-sm-6' style={{display: this.state.dealType === 'RENT' || 'none'}}>
                            <input
                                type='number'
                                min='0'
                                step='1'
                                name='basePrice'
                                pattern='^[0123456789]*$'
                                placeholder='قیمت رهن'
                                value={this.state.basePrice}
                                onChange={(event) => this.handleChange(event)}
                            />
                            <span className='badge'>تومان</span>
                        </div>
                        <div className='col-12 col-sm-6 vanish-sm'
                             style={{display: this.state.dealType === 'SELL' || 'none'}}>
                            &nbsp;
                        </div>
                        <div className='col-12 col-sm-6'>
                            <input
                                type='text'
                                name='address'
                                placeholder='آدرس'
                                value={this.state.address}
                                onChange={(event) => this.handleChange(event)}
                            />
                        </div>
                        <div className='col-12 col-sm-6'>
                            <input
                                type='text'
                                name='phone'
                                pattern='^0[0123456789]{10}$'
                                placeholder='تلفن (با کد شهر)'
                                value={this.state.phone}
                                onChange={(event) => this.handleChange(event)}
                            />
                        </div>
                        <div className='col-12'>
                            <input
                                type='text'
                                name='description'
                                placeholder='توضیحات'
                                value={this.state.description}
                                onChange={(event) => this.handleChange(event)}
                            />
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

export default withRouter(NewHouseForm);
