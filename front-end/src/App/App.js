import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import Footer from '../Footer/Footer'
import './main.css';
import './boxes.css';
import './boxes.css';
import './btn.css';

class App extends Component {
    render() {
        return (
            <Footer />
        );
    }
}

export default withRouter(App);
