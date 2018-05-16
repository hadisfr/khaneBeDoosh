var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');

router.get('/', (req, res) => {
    res.send({ balance: 200, name: 'بهنام همایون', username: 'behnam' });
});

module.exports = router;
