var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');

router.get('/', (req, res) => {
    var minArea = req.query.minArea;
    var buildingType = req.query.buildingType;
    var dealType = req.query.dealType;
    var minBasePrice, minRentPrice, minSellPrice;
    switch (dealType) {
        case 'RENT':
            minBasePrice = req.query.minBasePrice;
            minRentPrice = req.query.minRentPrice;
            break;
        case 'SELL':
            minSellPrice = req.query.minSellPrice;
            break;
    }
    res.send([
        {
            imageUrl:
                'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Soweto_township.jpg/320px-Soweto_township.jpg',
            id: 'ca27a86c-bf52-4d79-9834-90a48ff4be9b_1001',
            area: minArea,
            buildingType: buildingType,
            dealType: dealType,
            price: {
                sellPrice: minSellPrice
            }
        }
    ]);
});

router.get('/:id', (req, res) => {
    var houseId = req.params.id;
    res.send({
        imageUrl:
            'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Soweto_township.jpg/320px-Soweto_township.jpg',
        id: 'ca27a86c-bf52-4d79-9834-90a48ff4be9b_1001',
        area: 161,
        buildingType: 'APARTMENT',
        dealType: 'SELL',
        price: {
            sellPrice: 109813
        },
        address: 'دروازه غار',
        description:
            'از خانه برون آمد و بازار بیاراست، در وهم نگنجد که چه دلبند و چه شیرین'
    });
});

router.post('/', (req, res) => {
    res.status(HttpStatus.NOT_IMPLEMENTED).end();
});

module.exports = router;
