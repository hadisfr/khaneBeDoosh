var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
var debug = require('debug')('khanebedoosh:routes');

router.post('/', (req, res) => {
    var balance = req.body.balance;
    if (balance === undefined) res.status(HttpStatus.BAD_REQUEST).end();
    res.send({ succes: true });
});

module.exports = router;
