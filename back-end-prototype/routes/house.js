var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');
const House = require('../models/house');
const { BuildingType, toBuildingType } = require('../models/buildingType');
const { DealType, toDealType } = require('../models/dealType');

const h = new House(
    0,
    'ca27a86c-bf52-4d79-9834-90a48ff4be9b_1001',
    161,
    BuildingType.VILLA,
    DealType.APARTMENT,
    { sellPrice: 109813 },
    'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Soweto_township.jpg/320px-Soweto_township.jpg',
    'دروازه غار',
    'از خانه برون آمد و بازار بیاراست، در وهم نگنجد که چه دلبند و چه شیرین',
    '686-04-0693'
);

router.get('/', (req, res) => {
    try {
        var minArea = req.query.minArea;
        var buildingType =
            req.query.buildingType && toBuildingType(req.query.buildingType);
        var dealType = req.query.dealType && toDealType(req.query.dealType);
        var minBasePrice, minRentPrice, minSellPrice;
        var price = {};
        switch (dealType) {
            case DealType.RENT:
                price.minBasePrice = req.query.minBasePrice;
                price.minRentPrice = req.query.minRentPrice;
                break;
            case DealType.SELL:
                price.minSellPrice = req.query.minSellPrice;
                break;
        }
        res.send([h.shortJson]);
    } catch (err) {
        debug(err.stack);
        res.status(HttpStatus.BAD_REQUEST).end();
    }
});

router.get('/:id', (req, res) => {
    try {
        var houseId = req.params.id;
        res.send(h.json);
    } catch (err) {
        debug(err.stack);
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
    }
});

router.post('/', (req, res) => {
    res.status(HttpStatus.NOT_IMPLEMENTED).end();
});

module.exports = router;
