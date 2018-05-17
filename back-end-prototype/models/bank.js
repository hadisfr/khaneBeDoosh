'use strict';
var fetch = require('node-fetch');
var debug = require('debug')('khanebedoosh:models');

class Bank {
    constructor(uri, apiKey) {
        this._uri = uri;
        this._apiKey = apiKey;
    }

    get uri() {
        return this._uri;
    }

    get apiKey() {
        return this._apiKey;
    }

    async increaseBalance(username, amount) {
        if (amount < 0) throw 'Negative Amount';
        return (
            (await (await fetch(this.uri, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    apiKey: this.apiKey
                },
                body: JSON.stringify({
                    userId: username,
                    value: amount
                })
            })).json()).result === 'OK'
        );
    }
}

module.exports = Bank;
