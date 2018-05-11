import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import './SearchResults.css';
import frontend_api from '../front-end-api.json';

class SearchResult extends Component {
    render() {
        const det = this.props.house;
        return (
            <div className='col-lg-6 col-sm-12 box-wrapper'>
                <Link to={frontend_api.house_details + det.id}>
                    <div className='box-light-condensed'>
                        <img src={det && det.imageUrl} alt={det && det.imageUrl}/>
                        <span className={'badge box-badge btn ' + (det && (
                            det.dealType === 'SELL' ? 'btn-red' : det.dealType === 'RENT' ? 'btn-violet' : ''
                        ))}>{det && (
                            det.dealType === 'SELL' ? 'خرید' : det.dealType === 'RENT' ? 'رهن و اجاره' : ''
                        )}</span>
                        <div className='detail row'>
                            <div className='col-6'>{det && det.area} متر مربع</div>
                            <div className='col-6'>{det && (
                                det.buildingType === 'APARTMENT'
                                    ? 'آپارتمان'
                                    :
                                    det.buildingType === 'VILLA'
                                        ? 'ویلایی'
                                        : det.buildingType
                            )}</div>
                            <div className='col-12'>
                                <hr/>
                            </div>
                            {
                                (det && (
                                    det.dealType === 'SELL'
                                        ? <div className='col-6'>
                                            <span className='fas fa-dollar-sign fa-lg'></span>&nbsp;
                                            قیمت {det.price.sellPrice}
                                            <span className='unimportant'> تومان </span>
                                        </div>
                                        : det.dealType === 'RENT'
                                        ? <div className='col-6'>
                                            <span className='fas fa-dollar-sign fa-lg'></span>&nbsp;
                                            رهن {det.price.basePrice}
                                            <span className='unimportant'> تومان </span>
                                        </div>
                                        : null
                                ))
                            }
                            {
                                (det && (
                                    det.dealType === 'RENT'
                                        ? <div className='col-6'>
                                            <span className='fas fa-dollar-sign fa-lg'></span>&nbsp;
                                            اجاره {det.price.rentPrice}
                                            <span className='unimportant'> تومان </span>
                                        </div>
                                        : null
                                ))
                            }
                        </div>
                    </div>
                </Link>
            </div>
        );
    }
}

export default withRouter(SearchResult);
