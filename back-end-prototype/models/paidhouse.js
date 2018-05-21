'use strict';
module.exports = (sequelize, DataTypes) => {
    var PaidHouse = sequelize.define('PaidHouse', {
        individualId: DataTypes.STRING,
        ownerId: DataTypes.STRING,
        houseId: DataTypes.STRING
    }, {});
    PaidHouse.associate = function (models) {
        // associations can be defined here
    };
    return PaidHouse;
};