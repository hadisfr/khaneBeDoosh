import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import './main.css';
import './boxes.css';
import './boxes.css';
import './btn.css';

class App extends Component {
    render() {
        return (
            <div className="container-fluid main">
                <Header />
                <p>خانه‌به‌دوش</p>
                <Footer />
            </div>
        );
    }
}

export default withRouter(App);
