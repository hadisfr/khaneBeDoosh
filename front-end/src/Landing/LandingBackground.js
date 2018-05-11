import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import './Landing.css';

class LandingBackground extends Component {
    render() {
        const urls = [
            '/pics/banners/mahdiar-mahmoodi-452489-unsplash.jpg',
            '/pics/banners/casey-horner-533586-unsplash.jpg',
            '/pics/banners/luke-van-zyl-504032-unsplash.jpg',
            '/pics/banners/michal-kubalczyk-260909-unsplash.jpg',
        ];
        const index = Math.floor(Math.random() * urls.length);
        return (
            <div className='landing-background' style={{backgroundImage: `url(${urls[index]})`}}></div>
        );
    }
}

export default withRouter(LandingBackground);
