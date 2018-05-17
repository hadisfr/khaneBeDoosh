'use strict';
const User = require('../models/user');
var debug = require('debug')('khanebedoosh:models');

const PHONE_PRICE = 1000;

class Individual extends User {
    constructor(username, displayName, balance, paidHouses) {
        super(username);
        this._displayName = displayName;
        this._balance = balance;
        this._paidHouses = paidHouses || new Map();
        this._paidHouses.set(566, new Set([123]));
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
        if (balance < 0) throw 'Negative Balace';
        this._balance = balance >= 0 ? balance : 0;
    }

    get json() {
        var res = {};
        res.displayName = this.displayName;
        res.balance = this.balance;
        return { ...super.json, ...res };
    }

    get paidHouses() {
        return this._paidHouses;
    }

    hasPaidForHouse(houseId, ownerId) {
        return (
            this.paidHouses.has(ownerId) &&
            this.paidHouses.get(ownerId).has(houseId)
        );
    }

    payForHouse(houseId, ownerId) {
        try {
            this.balance -= PHONE_PRICE;
            if (!this.paidHouses.has(ownerId))
                this.paidHouses.set(ownerId, new Set());
            this.paidHouses.get(ownerId).add(houseId);
            return true;
        } catch (err) {
            return false;
        }
    }
}

module.exports = Individual;
