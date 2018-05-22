'use strict';
const models = require('./index');
const PHONE_PRICE = 1000;

module.exports = (sequelize, DataTypes) => {
    var Individual = sequelize.define(
        'Individual',
        {
            username: {
                type: DataTypes.STRING,
                get() {
                    return this.getDataValue('username');
                }
            },
            displayName: {
                type: DataTypes.STRING,
                get() {
                    return this.getDataValue('displayName');
                }
            },
            balance: {
                type: DataTypes.INTEGER,
                set(newBalance) {
                    if (newBalance < 0) newBalance = 0;
                    this.setDataValue('balance', newBalance);
                }
            },
            isAdmin: {
                type: DataTypes.BOOLEAN,
                get() {
                    return this.getDataValue('isAdmin');
                }
            }
        },
        {}
    );
    Individual.prototype.hasPaidForHouse = async function (houseId, ownerId) {
        let paidHouses = await models.sequelize.models.PaidHouse.findAll({
            where: {
                individualId: this.getDataValue('username')
            }
        });
        let i;
        for (i = 0; i < paidHouses.length; i++) {
            if (
                paidHouses[i].getDataValue(houseId) === houseId &&
                paidHouses[i].getDataValue(ownerId) === ownerId
            )
                return true;
        }
        return false;
    };
    Individual.prototype.payForHouse = async function (houseId, ownerId) {
        try {
            this.balance -= PHONE_PRICE;
            let paidBefore = await this.hasPaidForHouse(
                houseId,
                ownerId
            );
            if (!paidBefore)
                await models.PaidHouse.create({
                    individualId: this.username,
                    ownerId: ownerId,
                    houseId: houseId
                });
            return true;
        } catch (err) {
            return false;
        }
    };
    Individual.associate = function(models) {
        // associations can be defined here

    };
    return Individual;
};
