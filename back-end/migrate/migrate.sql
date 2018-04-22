CREATE TABLE Individual ("username" TEXT PRIMARY KEY NOT NULL, "balance" INTEGER, "displayName" TEXT);
INSERT INTO "Individual" ("username","balance","displayName") VALUES ("behnam","200","بهنام همایون");
CREATE TABLE PaidHouses ("IndividualId" TEXT NOT NULL, "OwnerId" TEXT NOT NULL, "HouseId" TEXT NOT NULL, PRIMARY KEY ("IndividualId", "OwnerId", "HouseId"));
INSERT INTO "PaidHouses" ("IndividualId","OwnerId","HouseId") VALUES ("behnam","acm","78278842-78eb-475f-a38a-a7001f86f644");
