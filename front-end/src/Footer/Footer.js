import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom'
import './Footer.css';

class Footer extends Component {
    render() {
        return (
            <div className="footer row">
                <div className="col-sm-12 col-lg-5">
                    تمامی حقوق مادی و معنوی این وب‌سایت متعلق به&nbsp;
                    <a href="http://hadisafari.ir/" target="_blank">هادی صفری</a>
                    &nbsp;و&nbsp;
                    <a href="#">محمدرضا طیرانیان</a>
                    &nbsp;است.
                </div>
                <div className="col-sm-0 col-lg-5 vanish-sm"></div>
                <div className="col-sm-12 col-lg-2">
                    <a href="https://twitter.com/" target="_blank" title="Twitter" className="social-logo">
                        <img src="pics/icons/Twitter_bird_logo_2012.svg.png" alt="twitter" />
                    </a>&nbsp;
                    <a href="http://telegram.org/" target="_blank" title="Telegram" className="social-logo">
                        <img src="pics/icons/200px-Telegram_logo.svg.png" alt="telegram" />
                    </a>&nbsp;
                    <a href="https://instagram.com/" target="_blank" title="Instagram" className="social-logo">
                        <img src="pics/icons/200px-Instagram_logo_2016.svg.png" alt="instagram" />
                    </a>
                </div>
            </div>
        );
    }
}

export default withRouter(Footer);
