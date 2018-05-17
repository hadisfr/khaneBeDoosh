var express = require('express');
var router = express.Router();
var HttpStatus = require('http-status-codes');
const Individual = require('../models/individual');
var debug = require('debug')('khanebedoosh:routes');

router.get('/', (req, res) => {
    res.send(new Individual('behnam', 'بهنام همایون', 200).json);
});

module.exports = router;
