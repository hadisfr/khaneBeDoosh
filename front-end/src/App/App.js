import React, {Component} from 'react';
import {Route, Switch, Redirect, withRouter} from 'react-router-dom';
import './main.css';
import './boxes.css';
import './boxes.css';
import './btn.css';
import HttpStatus from 'http-status-codes';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import PageTitle from '../PageTitle/PageTitle';
import ErrorMsg from '../ErrorMsg/ErrorMsg';
import Pay from '../Pay/Pay';
import Login from '../Login/Login';
import HouseDetails from '../HouseDetails/HouseDetails';
import SearchResults from '../SearchResults/SearchResults';
import Landing from '../Landing/Landing';
import LandingBackground from '../Landing/LandingBackground';
import NewHouseForm from '../NewHouseForm/NewHouseForm';
import backend_api from '../back-end-api.json';
import frontend_api from '../front-end-api.json';

function user(name, username, balance, isAdmin) {
    this.name = name;
    this.username = username;
    this.balance = balance;
    this.isAdmin = isAdmin === true || isAdmin === 'true' || isAdmin === 'True' || isAdmin === 'TRUE';
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
        this.setState((prev, props) => ({msg: prev.msg.concat(msg)}));
        setTimeout(this.clearMsg, this.msgPresenter.timeout);
    }

    clearMsg() {
        this.setState((prev, props) => ({msg: prev.msg.slice(1)}))
    }

    updateUserInfo() {
        fetch(backend_api.user, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + this.getToken(),
                },
            }
        )
            .then(
                (res) => (res.status === HttpStatus.OK ? res.json() : null),
                (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR))
            ).then(function (res) {
            this.setState(res ? {user: new user(res.name, res.username, res.balance, res.isAdmin)} : null);
        }.bind(this), (err) => (this.props.history.push(frontend_api.error + HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    getToken() {
        return localStorage.getItem('token');
    }

    render() {
        return (
            <div className='container-fluid main'>
                <Header user={this.state.user}/>
                {
                    (this.state.msg && this.state.msg.length > 0)
                    && <div className='msgbox'>{this.state.msg.reduce((prev, curr) => [prev, <br/>, curr])}</div>
                }
                <Route exact path={frontend_api.root} component={LandingBackground}/>
                <Switch>
                    <Route exact path={frontend_api.root}/>
                    <Route exact path={frontend_api.login} render={(props) => <PageTitle title='ورود به سایت'/>}/>
                    <Route exact path={frontend_api.pay} render={(props) => <PageTitle title='افزایش موجودی'/>}/>
                    <Route path={frontend_api.new_house} render={(props) => <PageTitle title='ثبت ملک جدید'/>}/>
                    <Route path={frontend_api.house_details + ':id'}
                           render={(props) => <PageTitle title='مشخصات کامل ملک'/>}/>
                    <Route path={frontend_api.search} render={(props) => <PageTitle title='نتایج جست‌وجو'/>}/>
                    <Route path={frontend_api.error + ':id([0-9]{3})'} render={(props) => <PageTitle title='خطا'/>}/>
                    <Redirect from='*' to={frontend_api.error + HttpStatus.NOT_FOUND}/>
                </Switch>
                <div className='cnt row'>
                    <div className='col-1'></div>
                    <div className='col-10 center-align'>
                        <Switch>
                            <Route exact path={frontend_api.root} render={(props) => <Landing/>}/>
                            <Route
                                exact path={frontend_api.login}
                                render={(props) => (
                                    this.state.user
                                        ? <Redirect to={frontend_api.root}/>
                                        :
                                        <Login
                                            user={this.state.user}
                                            callBack={this.updateUserInfo.bind(this)}
                                            msgPresenter={this.msgPresenter}
                                        />
                                )}
                            />
                            <Route
                                exact path={frontend_api.pay}
                                render={(props) => (
                                    this.state.user
                                        ? <Pay
                                            user={this.state.user}
                                            getToken={this.getToken.bind(this)}
                                            callBack={this.updateUserInfo.bind(this)}
                                            msgPresenter={this.msgPresenter}
                                        />
                                        : <Redirect to={frontend_api.error + HttpStatus.UNAUTHORIZED}/>
                                )}
                            />
                            <Route
                                path={frontend_api.new_house}
                                render={(props) => (
                                    this.state.user
                                        ? (<NewHouseForm
                                            msgPresenter={this.msgPresenter}
                                            getToken={this.getToken.bind(this)}
                                        />)
                                        : <Redirect to={frontend_api.error + HttpStatus.UNAUTHORIZED}/>
                                )}
                            />
                            <Route
                                path={frontend_api.house_details + ':id'}
                                render={(props) => <HouseDetails
                                    callBack={this.updateUserInfo.bind(this)}
                                    getToken={this.getToken.bind(this)}
                                />}
                            />
                            <Route path={frontend_api.search} render={(props) => <SearchResults
                                getToken={this.getToken.bind(this)}
                            />}/>
                            <Route path={frontend_api.error + ':id'} component={ErrorMsg}/>
                        </Switch>
                    </div>
                    <div className='col-1'></div>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default withRouter(App);
