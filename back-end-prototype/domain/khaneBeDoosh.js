'use strict';
const models = require('../models');
const Bank = require('./bank');
const User = require('./user');
const Individual = require('./individual');
const RealEstate = require('./realEstate');
const RealEstateAcm = require('./realEstateAcm');
const House = require('./house');
const BuildingType = require('../domain/buildingType').BuildingType;
const DealType = require('../domain/dealType').DealType;
var Sequelize = require('sequelize');
var debug = require('debug')('khanebedoosh:domain');
const Op = Sequelize.Op;

class KhaneBeDoosh {
    constructor() {
        if (!KhaneBeDoosh.instance) {
            this.bank = new Bank(
                'http://139.59.151.5:6664/bank/pay',
                'a1965d20-1280-11e8-87b4-496f79ef1988'
            );
            this.defaultUser = new Individual('behnam', 'بهنام همایون', 200);
            this.realEstates = {};
            this.realEstates[
                new RealEstateAcm().username
            ] = new RealEstateAcm();
            KhaneBeDoosh.instance = this;
        }
        return KhaneBeDoosh.instance;
    }

    async increaseBalance(username, amount) {
        var res = await this.bank.increaseBalance(username, amount);
        if (res){
            let user = (await this.getUser(username));
            user.balance += Number(amount);
            let thisUser = await models.Individual.findOne({where: {username: username}});
            await thisUser.updateAttributes({
                balance: user.balance
            });
        }
        return res;
    }

    async getUser(username) {
        let result = models.Individual.findOne({where:{
            username: username
        }});

        return username === this.currentUser.username ? this.currentUser : null;
    }

    get currentUser() {
        return this.defaultUser;
    }

    async updateRealEstates(){
        let whereExp = {
            expireTime: {
                [Op.lt]: new Date().getTime()
            }
        };
        let result = await models.RealEstate.findAll({where: whereExp});
        //TODO: convert result to realestate objects
    }

    async updateRealEstate(id){
        // find realEstateo from id
        // refresh db with new timestamp
        // add new houses to db
    }

    async searchHouses({ minArea, buildingType, dealType, maxPrice }) {
        await this.updateRealEstates();
        let whereExp = {};
        if(dealType)
            whereExp.dealType = dealType;
        if(buildingType)
            whereExp.buildingType = buildingType;
        if(minArea)
            whereExp.area = {
                [Op.gte]: minArea
            };
        if(maxPrice && dealType) {
            if (dealType === 'SELL') {
                whereExp.priceSell = {
                    [Op.lte]: maxPrice.priceSell
                };
            } else if(dealType === 'RENT'){
                whereExp.priceRent = {
                    [Op.lte]: maxPrice.priceRent
                };
                whereExp.priceBase = {
                    [Op.lte]: maxPrice.priceBase
                };
            }
        }
        let result = await models.House.findAll({where: whereExp});
        return [
            new House(
                this.defaultUser.username,
                'ca27a86c-bf52-4d79-9834-90a48ff4be9b_1001',
                161,
                BuildingType.APARTMENT,
                DealType.SELL,
                { sellPrice: 109813 },
                'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Soweto_township.jpg/320px-Soweto_township.jpg',
                'دروازه غار',
                'از خانه برون آمد و بازار بیاراست، در وهم نگنجد که چه دلبند و چه شیرین',
                '686-04-0693'
            )
        ];
    }

    async getHouse(houseId, ownerId) {
        let house = await models.House.findOne({
            where :{
                houseId: houseId,
                ownerId: ownerId
            }
        });
        debug('hello');
        await house.getDetails();
        return house.dataValues;
    }

    async getPhone(ownerId, houseId) {
        var user = this.currentUser;
        if (
            user.hasPaidForHouse(ownerId, houseId) ||
            (await user.payForHouse(ownerId, houseId))
        ) {
            return (await this.getHouse(ownerId, houseId)).phone;
        } else throw 'Not Enough Balance';
    }

    async isRealEstate(username) {
        return (await this.getUser(username)) instanceof RealEstate;
    }
}

const instance = new KhaneBeDoosh();

module.exports = instance;
