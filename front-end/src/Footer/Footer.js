import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import './Footer.css';
import SocialLogo from './SocialLogo';
import CopyrightOwner from './CopyrightOwner';

class Footer extends Component {
    render() {
        function social_logo(title, url, img) {
            this.title = title;
            this.url = url;
            this.img = img;
        }

        function copyright_owner(name, url) {
            this.name = name;
            this.url = url;
        }

        const social_logos = [
            new social_logo('Twitter', 'https://twitter.com/', '/pics/icons/Twitter_bird_logo_2012.svg.png'),
            new social_logo('Telegram', 'http://telegram.org/', '/pics/icons/200px-Telegram_logo.svg.png'),
            new social_logo('Instagram', 'https://instagram.com/', '/pics/icons/200px-Instagram_logo_2016.svg.png'),
        ]

        const copyright_owners = [
            new copyright_owner('هادی صفری', 'http://hadisafari.ir/'),
            new copyright_owner('محمدرضا طیرانیان'),
        ]

        return (
            <div className='footer row'>
                <div className='col-sm-12 col-lg-5'>
                    تمامی حقوق مادی و معنوی این وب‌سایت متعلق به&nbsp;
                    {copyright_owners.map((copyright_owner_inst) => (
                        <CopyrightOwner
                            key={copyright_owner_inst.name}
                            name={copyright_owner_inst.name}
                            website={copyright_owner_inst.url}
                        />
                    )).reduce((prev, curr) => [prev, ' و ', curr])}
                    &nbsp;است.
                </div>
                <div className='col-sm-0 col-lg-5 vanish-sm'></div>
                <div className='col-sm-12 col-lg-2'>
                    {social_logos.map((social_logo_inst) => (
                        <SocialLogo
                            key={social_logo_inst.title}
                            title={social_logo_inst.title}
                            url={social_logo_inst.url}
                            img={social_logo_inst.img}
                        />
                    ))}
                </div>
            </div>
        );
    }
}

export default withRouter(Footer);
