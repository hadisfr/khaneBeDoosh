'use strict';
var jwt = require('jsonwebtoken');
var debug = require('debug')('khanebedoosh:utility');

const HOUSE_ID_SECRET = 'Shahrbaraaz';

var encryptHouseId = (houseId, ownerId) => {
    return jwt.sign(
        {
            houseId: houseId,
            ownerId: ownerId
        },
        HOUSE_ID_SECRET
    );
};

var decryptHouseId = token => {
    return jwt.verify(token, HOUSE_ID_SECRET);
};

module.exports = {
    encryptHouseId: encryptHouseId,
    decryptHouseId: decryptHouseId
};
