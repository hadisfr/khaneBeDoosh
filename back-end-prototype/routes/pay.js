var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');

router.post('/', (req, res) => {
    var balance = req.body.balance;
    if (balance === undefined) res.status(HttpStatus.BAD_REQUEST).end();
    res.send({ succes: true });
});

module.exports = router;
