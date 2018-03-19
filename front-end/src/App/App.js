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
import backend_api from '../back-end-api.json'
import frontend_api from '../front-end-api.json'

function user(name, username, balance) {
    this.name = name;
    this.username= username;
    this.balance= balance;
}

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            msg: [],
        }
        this.setMsg = this.setMsg.bind(this);
        this.clearMsg = this.clearMsg.bind(this);
        this.msgPresenter = {
            timeout: 3000,
            showMsg: this.setMsg,
        }
    }

    componentDidMount() {
        this.updateUserInfo();
    }

    setMsg(msg) {
        this.setState((prev, props) => ({ msg: prev.msg.concat(msg) }));
        setTimeout(this.clearMsg, this.msgPresenter.timeout);
    }

    clearMsg() {
        this.setState((prev, props) => ({ msg: prev.msg.slice(1) }))
    }

    updateUserInfo() {
        fetch(backend_api.user).then((res) => (res.status === 200 ? res.json() : null)).then(function(res) {
            this.setState(res ? { user: new user(res.name, res.username, res.balance) } : null);
        }.bind(this));
    }

    render() {
        return (
            <div className="container-fluid main">
                <Header user={this.state.user} />
                {
                    (this.state.msg && this.state.msg.length > 0)
                    && <div className="msgbox">{this.state.msg.reduce((prev, curr) => [prev, <br />, curr])}</div>
                }
                <Route exact path={frontend_api.root} component={LandingBackground} />
                <Switch>
                    <Route exact path={frontend_api.root} />
                    <Route exact path={frontend_api.pay} render={(props) => <PageTitle title="افزایش موجودی" />} />
                    <Route path={frontend_api.house_details + ":id"} render={(props) => <PageTitle title="مشخصات کامل ملک" />} />
                    <Route path={frontend_api.search} render={(props) => <PageTitle title="نتایج جست‌وجو" />} />
                    <Route path={frontend_api.error + ":id([0-9]{3})"} render={(props) => <PageTitle title="خطا" />} />
                    <Redirect from="*" to={frontend_api.error + "404"} />
                </Switch>
                <div className="cnt row"><div className="col-1"></div><div className="col-10 center-align">
                    <Switch>
                        <Route exact path={frontend_api.root} render={(props) => <Landing />} />
                        <Route
                            exact path={frontend_api.pay}
                            render={(props) => (
                                this.state.user 
                                ? <Pay
                                    user={this.state.user}
                                    callBack={this.updateUserInfo.bind(this)}
                                    msgPresenter={this.msgPresenter}
                                />
                                : <Redirect to={frontend_api.root} />
                            )}
                        />
                        <Route path={frontend_api.house_details + ":id"} render={(props) => <HouseDetails />} />
                        <Route path={frontend_api.search} render={(props) => <SearchResults />} />
                        <Route path={frontend_api.error + ":id"} component={ErrorMsg} />
                    </Switch>
                </div><div className="col-1"></div></div>
                <Footer />
            </div>
        );
    }
}

export default withRouter(App);
