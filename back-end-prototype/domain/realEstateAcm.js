'use strict';
const House = require('./house');
const RealEstate = require('./realEstate');
const { BuildingType, toBuildingType } = require('../domain/buildingType');
const { DealType, toDealType } = require('../domain/dealType');
var fetch = require('node-fetch');
var debug = require('debug')('khanebedoosh:domain');

class RealEstateAcm extends RealEstate {
    constructor() {
        if (!RealEstateAcm.instance) {
            super(
                'acm',
                'http://139.59.151.5:6664/khaneBeDoosh/v2/house',
                async houseId => {
                    var house = (await (await fetch(
                        this.uri + '/' + houseId
                    )).json()).data;
                    return new House(
                        this.username,
                        house.id,
                        house.area,
                        toBuildingType(house.buildingType),
                        toDealType(house.dealType),
                        house.price,
                        house.imageURL,
                        house.address,
                        house.description,
                        house.phone
                    );
                },
                async () => {
                    var res = await (await fetch(this.uri)).json();
                    return {
                        houses: res.data.map(
                            house =>
                                new House(
                                    this.username,
                                    house.id,
                                    house.area,
                                    toBuildingType(house.buildingType),
                                    toDealType(house.dealType),
                                    house.price,
                                    house.imageURL,
                                    house.address
                                )
                        ),
                        expireTime: res.expireTime
                    };
                }
            );
            RealEstateAcm.instance = this;
        }
        return RealEstateAcm.instance;
    }
}

module.exports = RealEstateAcm;
