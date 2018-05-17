'use strict';
const User = require('../models/user');
var debug = require('debug')('khanebedoosh:models');

class RealEstate extends User {
    constructor(username, uri, getHouse, getHouses) {
        super(username);
        this._uri = uri;
        this._lastTimestamp = 0;
        this.getHouse = getHouse;
        this.getHouses = () => {
            var [houses, lastTimestamp] = getHouses();
            this._lastTimestamp = lastTimestamp;
            return houses;
        };
    }

    get uri() {
        return this._uri;
    }

    get lastTimestamp() {
        return this._lastTimestamp;
    }

    set lastTimestamp(lastTimestamp) {
        this._lastTimestamp = lastTimestamp;
    }

    get json() {
        var res = {};
        res.uri = this.uri;
        res.lastTimestamp = this.lastTimestamp;
        return { ...super.json, ...res };
    }
}

module.exports = RealEstate;
