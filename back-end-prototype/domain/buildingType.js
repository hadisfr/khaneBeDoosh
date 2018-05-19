'use strict';
var debug = require('debug')('khanebedoosh:domain');

const BuildingType = {
    VILLA: 'VILLA',
    APARTMENT: 'APARTMENT'
};

var parse = int => {
    switch (int) {
        case 0:
        case 'VILLA':
        case 'ویلایی':
        case 'ويلايي':
            return BuildingType.VILLA;
        case 1:
        case 'APARTMENT':
        case 'آپارتمان':
        case 'اپارتمان':
            return BuildingType.APARTMENT;
        default:
            throw 'Invalid BuildingType';
    }
};

module.exports = {
    BuildingType: Object.freeze(BuildingType),
    toBuildingType: parse
};
