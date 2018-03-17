import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import './Landing.css'
import SearchForm from '../SearchForm/SearchForm'
import Card from './Card'

class Landing extends Component {
    render() {
        function card(title, detail, img) {
            this.title = title;
            this.detail = detail;
            this.img = img;
        }

        const cards = [
            new card("آسان", "به‌سادگی صاحب خانه شوید.", "/pics/icons/726446.svg"),
            new card("مطمئن", "با خیال راحت به دنبال خانه بگردید.", "/pics/icons/726488.svg"),
            new card("گسترده", "در منطقهٔ مورد علاقهٔ خود صاحب خانه شوید.", "/pics/icons/726499.svg"),
        ];

        const why_use_us = [
            "اطلاعات کامل و صحیح از املاک قابل معامله",
            "بدون محدودیت، ۲۴ساعته و در تمامی ایام هفته",
            "جست‌وجوی هوشمند ملک، صرفه‌جویی در زمان",
            "تنوع در املاک، افزایش قدرت انتخاب مشترین",
            "بانکی جامع از اطلاعات هزاران آگهی به‌روز",
            "دستیابی به نتیجهٔ مطلوب در کمترین زمان ممکن",
            "همکاری با مشاوران املاک متخصص",
        ];

        return (
            <div className="row">
                <div id="first" className="col-12"><div className="row">
                    <div className="col-sm-0 col-lg-4 vanish-sm"></div>
                    <div id="logo-wrapper" className="col-12 col-lg-4">
                        <img className="logo" src="/pics/logo.svg" alt="khaneBeDoosh" />
                        <h1 className="logo white">خانه‌به‌دوش</h1>
                    </div>
                    <div className="col-sm-0 col-lg-4 vanish-sm"></div>
                    <div className="search">
                        <div className="box-wrapper col-12"><div className="box-dark col-12">
                            <SearchForm api={this.props.search_api} />
                        </div></div>
                    </div>
                </div></div>
                <div id="cards" className="col-12"> <div className="row">
                    {cards.map((card_inst) => <Card
                        key={card_inst.title}
                        title={card_inst.title}
                        detail={card_inst.detail}
                        img={card_inst.img}
                    />)}
                </div></div>
                <div id="why-use-us" className="col-12"><div className="row">
                    <h3 className="col-12">چرا خانه‌به‌دوش؟</h3>
                    <div className="col-12 col-lg-6"><ul>
                        {why_use_us.map((reason, i) => <li key={i}>{reason}</li>)}
                    </ul></div>
                    <div className="col-12 col-lg-6"><img src="/pics/icons/why-khanebedoosh.png" alt="why-khanebedoosh" /></div>
                </div></div>
            </div>
        );
    }
}

export default withRouter(Landing);
