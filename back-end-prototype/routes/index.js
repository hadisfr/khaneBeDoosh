var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
var debug = require('debug')('khanebedoosh:routes');
let models = require('../models');

router.get('/', (req, res) => {
    res.redirect('doc');
});

router.get('/doc', (req, res) => {
    res.sendStatus(HttpStatus.NOT_IMPLEMENTED).end();
});

// models.House.create({houseId : 'allah', dealType: 'SELL', buildingType: 'VILLA'}).then(house => {
//     debug(house.get('shortJson'));
// });

module.exports = router;
