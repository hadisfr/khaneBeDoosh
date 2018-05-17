'use strict';
const Bank = require('./bank');
var debug = require('debug')('khanebedoosh:models');

class KhaneBeDoosh {
    constructor() {
        if (!KhaneBeDoosh.instance) {
            this.bank = new Bank(
                'http://139.59.151.5:6664/bank/pay',
                'a1965d20-1280-11e8-87b4-496f79ef1988'
            );
            KhaneBeDoosh.instance = this;
        }
        return KhaneBeDoosh.instance;
    }

    async increaseBalance(username, amount) {
        return await this.bank.increaseBalance(username, amount);
    }
}

const instance = new KhaneBeDoosh();

module.exports = instance;
