var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
const khaneBeDoosh = require('../models/khaneBeDoosh');
var debug = require('debug')('khanebedoosh:routes');

router.post('/', (req, res) => {
    try {
        var balance = req.body.balance;
        if (balance === undefined) res.status(HttpStatus.BAD_REQUEST).end();
        khaneBeDoosh
            .increaseBalance(khaneBeDoosh.currentUser.username, balance)
            .then(ret =>
                res
                    .status(ret ? HttpStatus.OK : HttpStatus.BAD_GATEWAY)
                    .send({ succes: ret })
            )
            .catch(err => {
                debug(err);
                res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
            });
    } catch (err) {
        debug(err.stack);
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
    }
});

module.exports = router;
