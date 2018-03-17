import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './HouseDetails.css'

class HouseDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        fetch(this.props.api + "?id=" + this.props.match.params.id).then((res) => res.json()).then(function(res) {
            this.setState({ house_details: res });
        }.bind(this));
    }

    render() {
        const det = this.state.house_details;
        return (
            <div className="row">
                <div className="col-12 col-lg-4">
                    <button className="btn btn-violet">فروش</button>  {/* TODO: handle dealType */}
                    <dl id="house-detail">
                        <dt>شماره</dt><dd>۰۹۱۲۳۴۵۶۷۸۹</dd>  {/* TODO: handle phone number */}
                        <dt>نوع ساختمان</dt><dd>{det && (
                            det.buildingType == "APARTMENT" ? "آپارتمان" : det.buildingType == "VILLA" ? "ویلایی" : det.buildingType
                        )}</dd>
                        <dt>قیمت</dt><dd>۲٬۰۰۰٬۰۰۰٬۰۰۰ تومان</dd>  {/* TODO: add price based on dealType */}
                        <dt>آدرس</dt><dd>{det && det.detail.address}</dd>
                        <dt>متراژ</dt><dd>{det && det.area} متر مربع</dd>
                        <dt>توضیحات</dt><dd>{det && det.detail.description}</dd>
                    </dl>
                </div>
                <div className="col-12 col-lg-8">
                    <img id="house-pic" src={det && det.imageUrl} alt="house-pic" />
                    <button id="change-number-status" className="btn btn-red">نمایش شماره</button>  {/* TODO: handle phone number */}
                </div>
            </div>
        );
    }
}

export default withRouter(HouseDetails);
