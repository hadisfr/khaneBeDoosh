import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import './SearchResults.css';
import HttpStatus from 'http-status-codes';
import SearchResult from './SearchResult';
import SearchForm from '../SearchForm/SearchForm';
import backend_api from '../back-end-api.json';
import frontend_api from '../front-end-api.json';

class SearchResults extends Component {
    constructor(props) {
        super(props);
        this.state = {
            houses: []
        };
    }

    componentDidMount() {
        this.fetchResults(this.props.location.search);
    }

    fetchResults(query) {
        fetch(backend_api.search + query)
            .then(
                res =>
                    res.status === HttpStatus.OK
                        ? res.json()
                        : this.props.history.push(
                              frontend_api.error + res.status
                          ),
                err =>
                    this.props.history.push(
                        frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR
                    )
            )
            .then(
                function(res) {
                    if (res) this.setState({ houses: res });
                }.bind(this),
                err =>
                    this.props.history.push(
                        frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR
                    )
            );
    }

    render() {
        return (
            <div className="row SearchResults">
                <div className="center-align col-12 gray">
                    برای مشاهدهٔ اطلاعات بیشتر دربارهٔ هر خانه روی آن کلیک کنید.
                </div>
                <div className="search-results col-12">
                    <div className="row">
                        {this.state.houses.map(house => (
                            <SearchResult key={house.id} house={house} />
                        ))}
                    </div>
                </div>
                <div className="search col-12">
                    <div className="row">
                        <div className="center-align col-12 gray">
                            جست‌وجوی مجدد
                        </div>
                        <div className="box-wrapper col-12 gray">
                            <div className="box-dark">
                                <SearchForm
                                    call_back={this.fetchResults.bind(this)}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(SearchResults);
