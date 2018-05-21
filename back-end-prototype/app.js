var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var cors = require('cors');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var houseRouter = require('./routes/house');
var payRouter = require('./routes/pay');
var phoneRouter = require('./routes/phone');
var userRouter = require('./routes/user');

var models = require('./models');

var app = express();
app.set('logLevel', process.env.LOG_LEVEL || 'tiny');

app.use(logger(app.get('logLevel')));
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/house', houseRouter);
app.use('/pay', payRouter);
app.use('/phone', phoneRouter);
app.use('/user', userRouter);

models.Individual.create({
    username: 'behnam',
    displayName: 'بهنام همایون',
    balance: 2000,
    isAdmin: false
});

module.exports = app;
