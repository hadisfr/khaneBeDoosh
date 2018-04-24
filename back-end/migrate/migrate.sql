CREATE TABLE Individual (
    "username" TEXT PRIMARY KEY NOT NULL,
    "balance" INTEGER,
    "displayName" TEXT
);
INSERT INTO "Individual" ("username","balance","displayName")
    VALUES ("behnam","200","بهنام همایون");
CREATE TABLE PaidHouses (
    "individualId" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,
    "houseId" TEXT NOT NULL,
    PRIMARY KEY ("individualId", "ownerId", "houseId")
);
INSERT INTO "PaidHouses" ("individualId","ownerId","houseId")
    VALUES ("behnam","acm","78278842-78eb-475f-a38a-a7001f86f644");
CREATE TABLE RealEstate ("name" TEXT, "expireTime" INTEGER);
INSERT INTO "RealEstate" ("name", "expireTime")
    VALUES ("acm", 0);
CREATE TABLE House (
    "houseId" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,
    "area" INTEGER, "imageUrl" TEXT,
    "address" TEXT, "phone" TEXT,
    "description" TEXT,
    "buildingType" TEXT,
    "dealType" TEXT,
    "priceBase" INTEGER,
    "priceRent" INTEGER,
    "priceSell" INTEGER,
    PRIMARY KEY ("houseId", "ownerId")
);
INSERT INTO "House" ("houseId","ownerId","area","imageUrl","address","phone","description","buildingType","dealType","priceBase","priceRent","priceSell")
    VALUES ('sdfghjkcxjhjkojhlkmknzvnsdjlkfalkdnamxjamoddmodm','behnam','100','','UT','09123456789','?!!','VILLA','SELL',0,0,'100');
