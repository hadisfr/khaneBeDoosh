'use strict';
let models = require('../models');
const Bank = require('./bank');
const Individual = require('./individual');
const RealEstateAcm = require('./RealEstateAcm');
const Sequelize = require('sequelize');
const debug = require('debug')('khanebedoosh:domain');
const realEstateList = require('./realEstateList');
const Op = Sequelize.Op;

class KhaneBeDoosh {
    constructor() {
        if (!KhaneBeDoosh.instance) {
            // models.House.create({houseId : 'allah', dealType: 'SELL', buildingType: 'VILLA'});
            this.bank = new Bank(
                'http://139.59.151.5:6664/bank/pay',
                'a1965d20-1280-11e8-87b4-496f79ef1988'
            );
            this.defaultUsername = 'behnam';
            var realestateacm = new RealEstateAcm();
            realEstateList.addMember(realestateacm, realestateacm.username);
            this.updateRealEstate(realestateacm.username);
            KhaneBeDoosh.instance = this;
        }
        return KhaneBeDoosh.instance;
    }

    async increaseBalance(username, amount) {
        let currentUser = await models.Individual.findOne({
            where: { username: this.defaultUsername }
        });
        let res = await this.bank.increaseBalance(username, amount);
        if (res) {
            currentUser.balance += parseInt(amount);
            await currentUser.save();
        }
        return res;
    }

    async getRealEstate(username) {
        return realEstateList.getRealEstate(username);
    }

    async getCurrentUser() {
        let user = await models.Individual.findOne({
            where: { username: this.defaultUsername }
        });
        return user;
    }

    async updateRealEstates() {
        let whereExp = {
            expireTime: {
                [Op.lt]: new Date().getTime()
            }
        };
        let result = await models.RealEstate.findAll({ where: whereExp });
        debug(result);
        let i;
        for (i = 0; i < result.length; i++) {
            await this.updateRealEstate(result[i].name);
        }
    }

    async updateRealEstate(id) {
        if (realEstateList.isRealEstate(id)) {
            let thisRealEstate = realEstateList.getRealEstate(id);
            let newHoueses = await thisRealEstate.getHouses();
            let modelRealEstate = await models.RealEstate.findOne({
                where: {
                    name: thisRealEstate.username
                }
            });
            modelRealEstate.expireTime = thisRealEstate.lastTimestamp;
            await modelRealEstate.save();
            // refresh db with new timestamp
            await models.House.destroy({
                where: {
                    ownerId: id
                }
            });
            let kasifnewHouses = [];
            for (let i = 0; i < newHoueses.length; i++) {
                kasifnewHouses.push(newHoueses[i].modelJson);
            }
            await models.House.bulkCreate(kasifnewHouses);
            // add new houses to db
        }
    }

    async searchHouses({ minArea, buildingType, dealType, maxPrice }) {
        await this.updateRealEstates();
        let whereExp = {};
        if (dealType) whereExp.dealType = dealType;
        if (buildingType) whereExp.buildingType = buildingType;
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
        return await models.House.findAll({ where: whereExp });
    }

    async getHouse(houseId, ownerId) {
        let house = await models.House.findOne({
            where: {
                houseId: houseId,
                ownerId: ownerId
            }
        });
        await house.details;
        return house;
    }

    async getPhone(ownerId, houseId) {
        if (
            !(await this.getCurrentUser().get('hasPaidForHouse')(
                houseId,
                ownerId
            ))
        ) {
            if (
                await this.getCurrentUser().set('payForHouse')(houseId, ownerId)
            )
                return await this.getHouse(houseId, ownerId).get('phone');
        }
        throw 'Not Enough Balance';
    }

    isRealEstate(username) {
        return realEstateList.isRealEstate(username);
    }
}

const instance = new KhaneBeDoosh();

module.exports = instance;
