'use strict';
const khaneBeDoosh = require('./khanebedoosh');
const BuildingType = require('./buildingType').BuildingType;
const DealType = require('./dealType').DealType;
const encryptHouseId = require('../utility').encryptHouseId;
var debug = require('debug')('khanebedoosh:domain');

class House {
    constructor(
        ownerId,
        id,
        area,
        buildingType,
        dealType,
        price,
        imageUrl,
        address,
        description,
        phone
    ) {
        this._ownerId = ownerId;
        this._id = id;
        this._area = area;
        this._buildingType = buildingType;
        this._dealType = dealType;
        this._price = price;
        this._imageUrl = imageUrl;
        this._address = address;
        this._description = description || undefined;
        this._phone = phone || undefined;
    }

    get ownerId() {
        return this._ownerId;
    }

    get id() {
        return this._id;
    }

    get area() {
        return this._area;
    }

    get buildingType() {
        return this._buildingType;
    }

    get dealType() {
        return this._dealType;
    }

    get price() {
        return this._price;
    }

    get imageUrl() {
        return this._imageUrl;
    }

    get address() {
        return this._address;
    }

    get description() {
        return this._description;
    }

    get phone() {
        return this._phone;
    }

    async getDetails() {
        // has infinite loop with getDetails
        if (this._description !== undefined && this._phone !== undefined)
            return;
        if (khaneBeDoosh.isRealEstate(this.ownerId)) {
            let house = await (await khaneBeDoosh.getRealEstate(
                this.ownerId
            )).getHouse(this.id);
            this._description = house.description;
            this._phone = house.phone;
            debug(this.json);
        }
    }

    get modelJson() {
        let res = {};
        res.houseId = this.id;
        res.ownerId = this.ownerId;
        res.area = this.area;
        res.imageUrl = this.imageUrl;
        res.address = this.address;
        res.phone = this.phone;
        res.description = this.description;
        res.buildingType = this.buildingType;
        res.dealType = this.dealType;
        res.priceBase = this.price.priceBase;
        res.priceRent = this.price.priceRent;
        res.priceSell = this.price.priceSell;
        return res;
    }

    get shortJson() {
        let res = {};
        res.id = encryptHouseId(this.id, this.ownerId);
        res.area = this.area;
        res.buildingType = this.buildingType;
        res.dealType = this.dealType;
        res.price = this.price;
        res.imageUrl = this.imageUrl;
        res.address = this.address;
        return res;
    }

    get json() {
        let res = this.shortJson;
        res.description = this.description;
        return res;
    }
}

module.exports = House;
