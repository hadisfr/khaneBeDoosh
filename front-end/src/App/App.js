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
import HouseDetails from '../HouseDetails/HouseDetails'
import SearchResults from '../SearchResults/SearchResults'
import Landing from '../Landing/Landing'
import LandingBackground from '../Landing/LandingBackground'

function user(name, username, balance) {
    this.name = name;
    this.username= username;
    this.balance= balance;
    // TODO: request user's data from back-end in App::render before return
}

function api(root, sub) {
    this.root = root;
    Object.keys(sub).forEach((key) => (this[key] = root + "/" + sub[key]), this);
}

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: new user("بهنام همایون", "behnam", 200000),
            backend_api: new api("http://localhost:8080/khaneBeDoosh", {
                pay: "pay",
                house_details: "houseDetails",  // TODO: use back-end's API for house details
                search: "search",  // TODO: use back-end's API for search
            }),
            msg: [],
        }
        this.setMsg = this.setMsg.bind(this);
        this.clearMsg = this.clearMsg.bind(this);
        this.msgPresenter = {
            timeout: 3000,
            showMsg: this.setMsg,
        }
    }

    setMsg(msg) {
        this.setState((prev, props) => ({ msg: prev.msg.concat(msg) }));
        setTimeout(this.clearMsg, this.msgPresenter.timeout);
    }

    clearMsg() {
        this.setState((prev, props) => ({ msg: prev.msg.slice(1) }))
    }

    render() {
        return (
            <div className="container-fluid main">
                <Header user={this.state.user} />
                <Route exact path="/" component={LandingBackground} />
                <Switch>
                    <Route exact path="/" />
                    <Route exact path="/pay" render={(props) => <PageTitle title="افزایش موجودی" />} />
                    <Route path="/house/:id" render={(props) => <PageTitle title="مشخصات کامل ملک" />} />
                    <Route path="/house" render={(props) => <PageTitle title="نتایج جست‌وجو" />} />
                    <Route path="/err/:id([0-9]{3})" render={(props) => <PageTitle title="خطا" />} />
                    <Redirect from="*" to="/err/404" />
                </Switch>
                {
                    (this.state.msg && this.state.msg.length > 0)
                    && <div className="msgbox">{this.state.msg.reduce((prev, curr) => [prev, <br />, curr])}</div>
                }
                <div className="cnt row"><div className="col-1"></div><div className="col-10 center-align">
                    <Switch>
                        <Route
                            exact path="/"
                            render={(props) => <Landing search_api={this.state.backend_api.search} />}
                        />
                        <Route
                            exact path="/pay"
                            render={(props) => <Pay
                                user={this.state.user}
                                api={this.state.backend_api.pay}
                                msgPresenter={this.msgPresenter}
                            />}
                        />
                        <Route
                            path="/house/:id"
                            render={(props) => <HouseDetails api={this.state.backend_api.house_details} />}
                        />
                        <Route
                            path="/house"
                            render={(props) => <SearchResults api={this.state.backend_api.search} />}
                        />
                        <Route path="/err/:id" component={ErrorMsg} />
                    </Switch>
                </div><div className="col-1"></div></div>
                <Footer />
            </div>
        );
    }
}

export default withRouter(App);
