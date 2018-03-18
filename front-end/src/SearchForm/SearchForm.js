import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './SearchForm.css'

class SearchForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            buildingType: "",
            maxPrice: "",
            minArea: "",
            dealType: "0",
        };
    }

    handle_change(event) {
        if(event.target.type !== "radio")
            event.preventDefault();
        this.setState({ [event.target.name]: event.target.value });
    }

    handle_action(event) {
        event.preventDefault();
        const query = "?" + Object.keys(this.state).map((key) => (key + '=' + this.state[key])).join("&");
        this.props.history.push("/house" + query);
        if(this.props.call_back)
            this.props.call_back(query);
    }

    render() {
        // TODO: handle price for two dealTypes
        return (
            <form onSubmit={ (event) => this.handle_action(event) } ><div className="row search">
                <div className="col-12 col-sm-6 col-lg-4">
                    <select
                        name="buildingType"
                        onChange={ (event) => this.handle_change(event) }
                        value={this.state.buildingType}
                    >
                        <option value="" disabled>▼ نوع ملک</option>
                        <option value="VILLA">ویلایی</option>
                        <option value="APARTMENT">آپارتمان</option>
                    </select>
                </div>
                <div className="col-12 col-sm-6 col-lg-4">
                    <input
                        type="number"
                        name="maxPrice"
                        placeholder="حداکثر قیمت"
                        value={this.state.maxPrice}
                        onChange={ (event) => this.handle_change(event) }
                    />
                    <span className="badge">تومان</span>
                </div>
                <div className="col-12 col-sm-6 col-lg-4">
                    <input
                        type="number"
                        name="minArea"
                        placeholder="حداقل مساحت"
                        value={this.state.minArea}
                        onChange={ (event) => this.handle_change(event) }
                    />
                    <span className="badge">متر</span>
                </div>
                <div className="col-12 col-sm-6 col-lg-4"><fieldset>
                    <input
                        type="radio"
                        name="dealType"
                        value="0"
                        onChange={ (event) => this.handle_change(event) }
                        checked={ this.state.dealType === "0" }
                    />رهن و اجاره
                    <input
                        type="radio"
                        name="dealType"
                        value="1"
                        onChange={ (event) => this.handle_change(event) }
                        checked={ this.state.dealType === "1" }
                    />خرید
                </fieldset></div>
                <div className="col-12 col-sm-6 col-lg-4">&nbsp;</div>
                <div className="col-12 col-sm-6 col-lg-4"><input type="submit" className="btn btn-green" value="جست‌وجو" /></div>
            </div></form>
        );
    }
}

export default withRouter(SearchForm);
