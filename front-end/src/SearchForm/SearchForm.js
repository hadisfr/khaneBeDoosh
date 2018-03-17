import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './SearchForm.css'
import SearchResult from './SearchForm'

class SearchForm extends Component {
    render() {
        // TODO: make react single source of truth
        return (
            <form><div className="row">
                <div className="col-12 col-sm-6 col-lg-4"><select name="buildingType" defaultValue="NONE">
                    <option value="NONE" disabled>▼ نوع ملک</option>
                    <option value="VILLA">ویلایی</option>
                    <option value="APARTMENT">آپارتمان</option>
                </select></div>
                <div className="col-12 col-sm-6 col-lg-4">
                    <input type="number" name="maxPrice" placeholder="حداکثر قیمت" />
                    <span className="badge">تومان</span>
                </div>
                <div className="col-12 col-sm-6 col-lg-4">
                    <input type="number" name="minArea" placeholder="حداقل مساحت" />
                    <span className="badge">متر</span>
                </div>
                <div className="col-12 col-sm-6 col-lg-4"><fieldset>
                    <input type="radio" name="dealType" value="0" checked />رهن و اجاره
                    <input type="radio" name="dealType" value="1" />خرید
                </fieldset></div>
                <div className="col-12 col-sm-6 col-lg-4">&nbsp;</div>
                <div className="col-12 col-sm-6 col-lg-4"><input type="submit" className="btn btn-green" value="جست‌وجو" /></div>
            </div></form>
        );
    }
}

export default withRouter(SearchForm);
