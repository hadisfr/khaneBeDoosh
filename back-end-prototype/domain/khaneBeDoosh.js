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
            // var realestateacm = new RealEstateAcm();
            // this.realEstates[realestateacm.username] = realestateacm;
            KhaneBeDoosh.instance = this;
        }
        return KhaneBeDoosh.instance;
    }

    async increaseBalance(username, amount) {
        var res = await this.bank.increaseBalance(username, amount);
        if (res) {
            await models.Individual.set('balance', amount);
        }
        return res;
    }

    async getRealEstate(username) {
        return this.realEstates[username];
    }

    get currentUser() {
        return this.defaultUser;
    }

    async updateRealEstates() {
        let whereExp = {
            expireTime: {
                [Op.lt]: new Date().getTime()
            }
        };
        let result = await models.RealEstate.findAll({where: whereExp});
        let i;
        for (i = 0; i < result.length; i++) {
            await this.updateRealEstate(result[i].getDataValue('name'));
        }
    }

    async updateRealEstate(id) {
        if (this.realEstates.hasOwnProperty(id)) {
            let thisRealEstate = this.realEstates[id];
            let newHoueses = thisRealEstate.getHouses();
            let modelRealEstate = await models.RealEstate.findOne({where:{
                name: thisRealEstate.username
            }});
            modelRealEstate.expireTime = thisRealEstate.lastTimestamp;
            await modelRealEstate.save();
            // refresh db with new timestamp
            await models.House.destroy({where: {
                ownerId: id
            }});
            let kasifnewHouses = [];
            for(let i = 0; i < newHoueses.length; i++){
                kasifnewHouses.push(newHoueses[i].modelJson);
            }
            await models.House.bulkCreate(kasifnewHouses);
            // add new houses to db
        }
    }

    async searchHouses({minArea, buildingType, dealType, maxPrice}) {
        await this.updateRealEstates();
        let whereExp = {};
        if (dealType)
            whereExp.dealType = dealType;
        if (buildingType)
            whereExp.buildingType = buildingType;
        if (minArea)
            whereExp.area = {
                [Op.gte]: minArea
            };
        if (maxPrice && dealType) {
            if (dealType === 'SELL') {
                whereExp.priceSell = {
                    [Op.lte]: maxPrice.priceSell
                };
            } else if (dealType === 'RENT') {
                whereExp.priceRent = {
                    [Op.lte]: maxPrice.priceRent
                };
                whereExp.priceBase = {
                    [Op.lte]: maxPrice.priceBase
                };
            }
        }
        return await models.House.findAll({where: whereExp});
    }

    async getHouse(houseId, ownerId) {
        let house = await models.House.findOne({
            where: {
                houseId: houseId,
                ownerId: ownerId
            }
        });
        await house.get('details');
        return house;
    }

    async getPhone(ownerId, houseId) {
        if (!(await models.Individual.get('hasPaidForHouse')(houseId, ownerId))) {
            if(await models.Individual.set('payForHouse')(houseId, ownerId))
                return await this.getHouse(houseId, ownerId).get('phone');
        }
        throw 'Not Enough Balance';
    }

    isRealEstate(username) {
        return (this.realEstates.hasOwnProperty(username));
    }
}

const instance = new KhaneBeDoosh();

module.exports = instance;
