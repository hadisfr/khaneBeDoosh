'use strict';
const {BuildingType, toBuildingType} = require('../domain/buildingType').BuildingType;
const {DealType, toDealType} = require('../domain/dealType');
const encryptHouseId = require('../utility').encryptHouseId;
// const khaneBeDoosh = require('../domain/khaneBeDoosh');

module.exports = (sequelize, DataTypes) => {
    var House = sequelize.define('House', {
        houseId: DataTypes.STRING,
        ownerId: DataTypes.STRING,
        area: DataTypes.INTEGER,
        imageUrl: DataTypes.STRING,
        address: DataTypes.STRING,
        phone: DataTypes.STRING,
        description: DataTypes.STRING,
        buildingType: {
            type: DataTypes.STRING,
            get() {
                return toBuildingType(this.getDataValue('buildingType'));
            }
        },
        dealType: {
            type: DataTypes.STRING,
            get() {
                return toDealType(this.getDataValue('dealType'));
            }
        },
        priceBase: DataTypes.INTEGER,
        priceRent: DataTypes.INTEGER,
        priceSell: DataTypes.INTEGER,
        toDelete: DataTypes.BOOLEAN
    }, {
        getterMethods: {
            price() {
                var rel = {};
                if (this.dealType === DealType.SELL) {
                    rel.sellPrice = this.priceSell;
                } else if (this.dealType === DealType.RENT) {
                    rel.rentPrice = this.priceRent;
                    rel.basePrice = this.priceBase;
                }
                return rel;
            },
            shortJson() {
                var res = {};
                res.id = encryptHouseId(this.getDataValue('houseId'), this.getDataValue('ownerId'));
                res.area = this.getDataValue('area');
                res.buildingType = this.getDataValue('buildingType');
                res.dealType = this.getDataValue('dealType');
                res.price = this.price;
                res.imageUrl = this.getDataValue('imageUrl');
                res.address = this.getDataValue('address');
                return res;
            },
            json() {
                var res = this.get('shortJson');
                res.description = this.getDataValue('description');
                return res;
            },
            async details() {
                if (this.description !== undefined && this.phone !== undefined)
                    return;
                // if (khaneBeDoosh.isRealEstate(this.ownerId)) {
                //     let house = await (await khaneBeDoosh.getRealEstate(
                //         this.ownerId
                //     )).getHouse(this.id);
                //     await this.setDataValue('description', house.description);
                //     await this.setDataValue('phone', house.phone);
                // }
            }
        },
        setterMethods: {
            price(price) {
                if (this.dealType === DealType.SELL) {
                    this.setDataValue('priceSell', price.sellPrice);
                } else if (this.dealType === DealType.RENT) {
                    this.setDataValue('priceRent', price.rentPrice);
                    this.setDataValue('priceBase', price.basePrice);
                }
            }
        }
    });
    House.associate = function (models) {
        // associations can be defined here
    };
    return House;
};