import React, { Component } from 'react';
import { Link , withRouter } from 'react-router-dom'
import './SearchResults.css'

class SearchResult extends Component {
    render() {
        const det = this.props.house;
        return (
            <div className="col-lg-6 col-sm-12 box-wrapper">
                <Link to={det.id}>
                    <div className="box-light-condensed">
                        <img src={det && det.imageUrl} alt="picture of house" />
                        <span className="badge box-badge btn-red">رهن و اجاره</span>  {/* TODO: handle dealType and change color */}
                        <div className="detail row">
                            <div className="col-6">{det && det.area} متر مربع</div>
                            <div className="col-6">
                                <span className="fas fa-map-marker-alt red"></span>  {/* TODO: handle dealType and change color */}
                                &nbsp;
                                {det && det.detail && det.detail.address}
                            </div>
                            <div className="col-12"><hr /></div>
                            <div className="col-6">رهن ۲۰۰٬۰۰۰٬۰۰۰ <span className="unimportant"> تومان </span></div>  {/* TODO: add price based on dealType */}
                            <div className="col-6">اجاره ۱۵۰٬۰۰۰٬۰۰۰  <span className="unimportant"> تومان </span></div>  {/* TODO: add price based on dealType */}
                        </div>
                    </div>
                </Link>
            </div>
        );
    }
}

export default withRouter(SearchResult);