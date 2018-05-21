var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
const khaneBeDoosh = require('../domain/khaneBeDoosh');
var debug = require('debug')('khanebedoosh:routes');

router.get('/', (req, res) => {
    try {
        khaneBeDoosh.getCurrentUser().then(user => {
            res.send(user.json);
        }).catch(err => {
            debug(err.stack);
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
        });
    } catch (err) {
        debug(err.stack);
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
    }
});

module.exports = router;
