'use strict';
const BuildingType = require('./buildingType').BuildingType;
const DealType = require('./dealType').DealType;
const encryptHouseId = require('../utility').encryptHouseId;
var debug = require('debug')('khanebedoosh:models');

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
        if (this._description === undefined) this.getDetails();
        return this._description;
    }

    get phone() {
        if (this._phone === undefined) this.getDetails();
        return this._phone;
    }

    getDetails() {
        // if (isRealEstate(this.ownerId)) {
        //     var details = getOwner(this.ownerId).getDetails(this.id);
        //     this._description = details.description;
        //     this._phone = details.phone;
        // }
    }

    get shortJson() {
        var res = {};
        res.id = encryptHouseId(this.id, this.ownerId);
        res.area = this.area;
        res.buildingType = this.buildingType;
        res.dealType = this.dealType;
        res.imageUrl = this.imageUrl;
        res.address = this.address;
        return res;
    }

    get json() {
        var res = this.shortJson;
        res.description = this.description;
        res.phone = this.phone;
        return res;
    }
}

module.exports = House;
