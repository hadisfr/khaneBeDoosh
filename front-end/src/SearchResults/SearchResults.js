import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './SearchResults.css'
import SearchResult from './SearchResult'
import SearchForm from '../SearchForm/SearchForm'
import backend_api from '../back-end-api.json'

class SearchResults extends Component {
    constructor(props) {
        super(props);
        this.state = {
            houses: []
        };
    }

    componentDidMount() {
        this.fetch_results(this.props.location.search);
    }

    fetch_results(query) {
        console.log(query);
        fetch(backend_api.search + query).then((res) => res.json()).then(function(res) {
            this.setState({ houses: res });
        }.bind(this));
    }

    render() {
        return (
            <div className="row SearchResults">
                <div className="center-align col-12 gray">برای مشاهدهٔ اطلاعات بیشتر دربارهٔ هر خانه روی آن کلیک کنید.</div>
                <div className="search-results col-12"><div className="row">
                    {this.state.houses.map((house) => <SearchResult key={house.id} house={house} />)}
                </div></div>
                <div className="search col-12"><div className="row">
                    <div className="center-align col-12 gray">جست‌وجوی مجدد</div>
                    <div className="box-wrapper col-12 gray"><div className="box-dark">
                        <SearchForm call_back={ this.fetch_results.bind(this) } />
                    </div></div>
                </div></div>
            </div>
        );
    }
}

export default withRouter(SearchResults);
