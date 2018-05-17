'use strict';
class User {
    constructor(username) {
        this._username = username;
    }
    get username() {
        return this._username;
    }
    set username(username) {
        this._username = username;
    }
    get json() {
        var res = {};
        res.username = this.username;
        return res;
    }
}

module.exports = User;
