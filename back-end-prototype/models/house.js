'use strict';
module.exports = (sequelize, DataTypes) => {
  var House = sequelize.define('House', {
    houseId: DataTypes.STRING,
    ownerId: DataTypes.STRING,
    area: DataTypes.INTEGER,
    imageUrl: DataTypes.STRING,
    address: DataTypes.STRING,
    phone: DataTypes.STRING,
    description: DataTypes.STRING,
    buildingType: DataTypes.STRING,
    dealType: DataTypes.STRING,
    priceBase: DataTypes.INTEGER,
    priceRent: DataTypes.INTEGER,
    priceSell: DataTypes.INTEGER,
    toDelete: DataTypes.BOOLEAN
  }, {});
  House.associate = function(models) {
    // associations can be defined here
  };
  return House;
};