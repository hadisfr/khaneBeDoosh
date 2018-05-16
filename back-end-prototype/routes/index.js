var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');

router.get('/', (req, res) => {
    res.redirect('doc');
});

router.get('/doc', (req, res) => {
    res.sendStatus(HttpStatus.NOT_IMPLEMENTED).end();
});

module.exports = router;
