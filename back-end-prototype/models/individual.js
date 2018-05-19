'use strict';
module.exports = (sequelize, DataTypes) => {
  var Individual = sequelize.define('Individual', {
    username: DataTypes.STRING,
    displayName: DataTypes.STRING,
    balance: DataTypes.INTEGER,
    isAdmin: DataTypes.BOOLEAN
  }, {});
  Individual.associate = function(models) {
    // associations can be defined here
  };
  return Individual;
};