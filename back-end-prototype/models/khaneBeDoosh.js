'use strict';
const Bank = require('./bank');
const User = require('./user');
const Individual = require('./individual');
const RealEstate = require('./realEstate');
const House = require('./house');
var debug = require('debug')('khanebedoosh:models');

class KhaneBeDoosh {
    constructor() {
        if (!KhaneBeDoosh.instance) {
            this.bank = new Bank(
                'http://139.59.151.5:6664/bank/pay',
                'a1965d20-1280-11e8-87b4-496f79ef1988'
            );
            this.defaultUser = new Individual('behnam', 'بهنام همایون', 200);
            this.houses = [];
            KhaneBeDoosh.instance = this;
        }
        return KhaneBeDoosh.instance;
    }

    async increaseBalance(username, amount) {
        var res = await this.bank.increaseBalance(username, amount);
        if (res) (await this.getUser(username)).balance += Number(amount);
        return res;
    }

    async getUser(username) {
        return username === this.currentUser.username ? this.currentUser : null;
    }

    get currentUser() {
        return this.defaultUser;
    }
}

const instance = new KhaneBeDoosh();

module.exports = instance;
