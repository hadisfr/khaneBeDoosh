var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
const khaneBeDoosh = require('../domain/khaneBeDoosh');
const decryptHouseId = require('../utility').decryptHouseId;
var debug = require('debug')('khanebedoosh:routes');

router.get('/:id', (req, res) => {
    try {
        var { ownerId, houseId } = decryptHouseId(req.params.id);
        khaneBeDoosh
            .getPhone(ownerId, houseId)
            .then(phone => res.send({ phone: phone }))
            .catch(err => {
                if (err === 'Not Enough Balance') {
                    debug(err);
                    res.status(HttpStatus.PAYMENT_REQUIRED).end();
                } else {
                    debug(err.stack);
                    res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
                }
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

module.exports = router;
