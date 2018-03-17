import React, { Component } from 'react';
import { Route, Switch, Redirect, withRouter } from 'react-router-dom'
import './main.css';
import './boxes.css';
import './boxes.css';
import './btn.css';
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import PageTitle from '../PageTitle/PageTitle'
import ErrorMsg from '../ErrorMsg/ErrorMsg'
import Pay from '../Pay/Pay'

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
                <Switch>
                    <Route exact path="/" />
                    <Route exact path="/pay" render={(props) => <PageTitle title="افزایش موجودی" />} />
                    <Route path="/search" render={(props) => <PageTitle title="نتایج جست‌وجو" />} />
                    <Route path="/house/:id" render={(props) => <PageTitle title="مشخصات کامل ملک" />} />
                    <Route path="/err/:id([0-9]{3})" render={(props) => <PageTitle title="خطا" />} />
                    <Redirect from="*" to="/err/404" />
                </Switch>
                <div className="cnt row"><div className="col-1"></div><div className="col-10 center-align">
                    <Switch>
                        <Route exact path="/" />
                        <Route exact path="/pay" render={(props) => <Pay user={this.state.user} />} />
                        <Route path="/search" />
                        <Route path="/house/:id" />
                        <Route path="/err/:id" component={ErrorMsg} />
                    </Switch>
                </div><div className="col-1"></div></div>
                <Footer />
            </div>
        );
    }
}

export default withRouter(App);
