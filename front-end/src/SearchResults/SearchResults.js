import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './SearchResults.css'
import SearchResult from './SearchResult'

class SearchResults extends Component {
    constructor(props) {
        super(props);
        this.state = {
            houses: []
        };
    }

    componentDidMount() {
        fetch(this.props.api + this.props.location.search).then((res) => res.json()).then(function(res) {
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
                    <div className="box-wrapper col-12"><div className="box-dark col-12">
                        SearchForm
                    </div></div>
                </div></div>
            </div>
        );
    }
}

export default withRouter(SearchResults);
