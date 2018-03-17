import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import './main.css';
import './boxes.css';
import './boxes.css';
import './btn.css';
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import PageTitle from '../PageTitle/PageTitle'

function user(username, balance) {
    this.username= username;
    this.balance= balance;
}

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: new user("بهنام همایون", 200000),
        }
    }

    render() {
        return (
            <div className="container-fluid main">
                <Header user={this.state.user} />
                <PageTitle title="خانه‌به‌دوش" />
                <Footer />
            </div>
        );
    }
}

export default withRouter(App);
