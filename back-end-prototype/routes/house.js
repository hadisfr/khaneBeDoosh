var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
const khaneBeDoosh = require('../models/khanebedoosh');
const House = require('../models/house');
const { BuildingType, toBuildingType } = require('../models/buildingType');
const { DealType, toDealType } = require('../models/dealType');
const decryptHouseId = require('../utility').decryptHouseId;
var debug = require('debug')('khanebedoosh:routes');

router.get('/', (req, res) => {
    try {
        var filter = {};
        filter.minArea = req.query.minArea;
        filter.buildingType =
            req.query.buildingType && toBuildingType(req.query.buildingType);
        filter.dealType = req.query.dealType && toDealType(req.query.dealType);
        filter.maxPrice = {};
        switch (filter.dealType) {
            case DealType.RENT:
                filter.maxPrice.maxBasePrice = req.query.maxBasePrice;
                filter.maxPrice.maxRentPrice = req.query.maxRentPrice;
                break;
            case DealType.SELL:
                filter.maxPrice.maxSellPrice = req.query.maxSellPrice;
                break;
        }
        khaneBeDoosh
            .searchHouses(filter)
            .then(ret => res.send(ret.map(house => house.shortJson)))
            .catch(err => {
                debug(err.stack);
                res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
            });
    } catch (err) {
        debug(err.stack);
        res.status(HttpStatus.BAD_REQUEST).end();
    }
});

router.get('/:id', (req, res) => {
    try {
        var { ownerId, houseId } = decryptHouseId(req.params.id);
        khaneBeDoosh
            .getHouse(ownerId, houseId)
            .then(house => res.send(house.json))
            .catch(err => {
                debug(err.stack);
                res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
            });
    } catch (err) {
        if (err.name === 'JsonWebTokenError') {
            debug(err.name + ': ' + err.message);
            res.status(HttpStatus.BAD_REQUEST).end();
        } else {
            debug(err.stack);
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
        }
    }
});

router.post('/', (req, res) => {
    res.status(HttpStatus.NOT_IMPLEMENTED).end();
});

module.exports = router;
