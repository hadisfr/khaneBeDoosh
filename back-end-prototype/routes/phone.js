var express = require('express');
var router = express.Router();
var debug = require('debug')('khanebedoosh:routes');
var HttpStatus = require('http-status-codes');

router.get('/:id', (req, res) => {
    var houseId = req.params.id;
    var hasPaid = true;
    if (hasPaid) {
        res.send({ phone: '686-04-0693' });
    } else res.status(HttpStatus.PAYMENT_REQUIRED).end();
});

module.exports = router;
