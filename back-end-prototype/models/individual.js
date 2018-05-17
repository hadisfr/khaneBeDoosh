'use strict';
const User = require('../models/user');
const HashMap = require('hashmap');

class Individual extends User {
    constructor(username, displayName, balance) {
        super(username);
        this._displayName = displayName;
        this._balance = balance;
        this._paidHouses = new HashMap();
    }
    get displayName() {
        return this._displayName;
    }
    set displayName(displayName) {
        this._displayName = displayName;
    }
    get balance() {
        return this._balance;
    }
    set balance(balance) {
        this._balance = balance;
    }
    get json() {
        var res = {};
        res.displayName = this.displayName;
        res.balance = this.balance;
        return { ...super.json, ...res };
    }
}

module.exports = Individual;
