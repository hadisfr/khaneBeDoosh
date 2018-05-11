import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import './HouseDetails.css';
import HttpStatus from 'http-status-codes';
import backend_api from '../back-end-api.json';
import frontend_api from '../front-end-api.json';

class HouseDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        fetch(backend_api.house_details + '?id=' + this.props.match.params.id)
            .then(
                (res) => (res.status === HttpStatus.OK ? res.json() : this.props.history.push(frontend_api.error + res.status)),
                (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR))
            ).then(function (res) {
            this.setState({house_details: res});
        }.bind(this), (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    getPhone(event) {
        fetch(backend_api.get_phone + '?id=' + this.props.match.params.id, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + this.props.getToken(),
            },
        })
            .then(
                (res) => (res.status === HttpStatus.OK ? res.json() : this.props.history.push(frontend_api.error + res.status)),
                (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR))
            ).then(function (res) {
            this.setState({phone: res.phone});
            this.props.callBack();
        }.bind(this), (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    render() {
        const det = this.state.house_details;
        return (
            <div className='row'>
                <div className='col-12 col-lg-4'>
                    <button className={'btn ' + (det && (
                        det.dealType === 'SELL' ? 'btn-red' : det.dealType === 'RENT' ? 'btn-violet' : ''
                    ))}>{det && (
                        det.dealType === 'SELL' ? 'خرید' : det.dealType === 'RENT' ? 'رهن و اجاره' : null
                    )}</button>
                    <dl id='house-detail'>
                        <dt>نوع ساختمان</dt>
                        <dd>{det && (
                            det.buildingType === 'APARTMENT'
                                ? 'آپارتمان'
                                : det.buildingType === 'VILLA'
                                ? 'ویلایی'
                                : det.buildingType
                        )}</dd>
                        {(det && (
                            det.dealType === 'SELL'
                                ? <dt>قیمت فروش</dt>
                                : det.dealType === 'RENT'
                                ? <dt>قیمت رهن</dt>
                                : null
                        ))}
                        {(det && (
                            det.dealType === 'SELL'
                                ? <dd>{det.price.sellPrice} تومان</dd>
                                : det.dealType === 'RENT'
                                ? <dd>{det.price.basePrice} تومان</dd>
                                : null
                        ))}
                        {(det && (
                            det.dealType === 'RENT'
                                ? <dt>قیمت اجاره</dt>
                                : null
                        ))}
                        {(det && (
                            det.dealType === 'RENT'
                                ? <dd>{det.price.rentPrice} تومان</dd>
                                : null
                        ))}
                        <dt>آدرس</dt>
                        <dd>{(det && det.address) || <span>&nbsp;</span>}</dd>
                        <dt>متراژ</dt>
                        <dd>{det && det.area} متر مربع</dd>
                        {!this.state.phone && <button
                            id='change-number-status'
                            className='btn btn-green'
                            onClick={(event) => (this.getPhone(event))}
                        >نمایش شماره</button>}
                        {this.state.phone !== undefined && <dt>شماره</dt>}
                        {this.state.phone !== undefined && <dd>{this.state.phone}</dd>}
                        <dt>توضیحات</dt>
                        <dd>{(det && det.description) || <span>&nbsp;</span>}</dd>
                    </dl>
                </div>
                <div className='col-12 col-lg-8'>
                    <img id='house-pic' src={det && det.imageUrl} alt={det && det.imageUrl}/>
                </div>
            </div>
        );
    }
}

export default withRouter(HouseDetails);
