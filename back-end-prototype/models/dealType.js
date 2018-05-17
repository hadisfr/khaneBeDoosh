'use strict';
var debug = require('debug')('khanebedoosh:models');

const DealType = {
    SELL: 'SELL',
    RENT: 'RENT'
};

var parse = int => {
    switch (int) {
        case 0:
        case 'SELL':
        case 'فروش':
            return DealType.SELL;
        case 1:
        case 'RENT':
        case 'اجاره':
            return DealType.RENT;
        default:
            throw 'Invalid DealType';
    }
};

module.exports = {
    DealType: Object.freeze(DealType),
    toDealType: parse
};
