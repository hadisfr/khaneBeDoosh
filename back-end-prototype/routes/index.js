var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
var debug = require('debug')('khanebedoosh:routes');

router.get('/', (req, res) => {
    res.redirect('doc');
});

router.get('/doc', (req, res) => {
    res.sendStatus(HttpStatus.NOT_IMPLEMENTED).end();
});

module.exports = router;
