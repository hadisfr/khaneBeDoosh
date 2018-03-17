import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import Footer from '../Footer/Footer'

class App extends Component {
    render() {
        return (
            <Footer />
        );
    }
}

export default withRouter(App);
