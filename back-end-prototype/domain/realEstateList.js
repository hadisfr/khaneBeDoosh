'use strict';

class RealEstateList {
    constructor() {
        if (!RealEstateList.instance) {
            this.list = {};
            RealEstateList.instance = this;
        }
        return RealEstateList.instance;
    }

    addMember(member, key) {
        this.list.key = member;
    }

    isRealEstate(name) {
        return this.list.hasOwnProperty(name);
    }

    getRealEstate(name) {
        return this.list[name];
    }
}

const instance = new RealEstateList();

module.exports = instance;