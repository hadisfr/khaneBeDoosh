CREATE TABLE Individual ("username" TEXT PRIMARY KEY NOT NULL, "balance" INTEGER, "displayName" TEXT);
INSERT INTO "Individual" ("username","balance","displayName") VALUES ("behnam","200","بهنام همایون");
CREATE TABLE PaidHouses ("individualId" TEXT NOT NULL, "ownerId" TEXT NOT NULL, "houseId" TEXT NOT NULL, PRIMARY KEY ("individualId", "ownerId", "houseId"));
INSERT INTO "PaidHouses" ("individualId","ownerId","houseId") VALUES ("behnam","acm","78278842-78eb-475f-a38a-a7001f86f644");
CREATE TABLE Realestate ("name" TEXT, "expireTime" DATETIME);
