'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Houses', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      houseId: {
        type: Sequelize.STRING
      },
      ownerId: {
        type: Sequelize.STRING
      },
      area: {
        type: Sequelize.INTEGER
      },
      imageUrl: {
        type: Sequelize.STRING
      },
      address: {
        type: Sequelize.STRING
      },
      phone: {
        type: Sequelize.STRING
      },
      description: {
        type: Sequelize.STRING
      },
      buildingType: {
        type: Sequelize.STRING
      },
      dealType: {
        type: Sequelize.STRING
      },
      priceBase: {
        type: Sequelize.INTEGER
      },
      priceRent: {
        type: Sequelize.INTEGER
      },
      priceSell: {
        type: Sequelize.INTEGER
      },
      toDelete: {
        type: Sequelize.BOOLEAN
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE
      }
    });
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('Houses');
  }
};