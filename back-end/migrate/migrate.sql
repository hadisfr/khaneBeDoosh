PRAGMA foreign_keys = ON;

CREATE TABLE "User" (
    "name" TEXT PRIMARY KEY NOT NULL
);

CREATE TABLE "Individual" (
    "username" TEXT PRIMARY KEY NOT NULL REFERENCES "User" ON DELETE CASCADE ON UPDATE CASCADE,
    "displayName" TEXT,
    "balance" INTEGER DEFAULT 0,
    "isAdmin" BOOLEAN DEFAULT FALSE,
    "passwordHash" TEXT,
    "passwordSalt" TEXT
);
CREATE TRIGGER "Individual_Creation"
    BEFORE INSERT ON "Individual"
    BEGIN
        INSERT INTO "User"
            VALUES (NEW."username");
    END;
CREATE TRIGGER "Individual_Delete"
    BEFORE DELETE ON "Individual"
    BEGIN
        DELETE FROM "User"
            WHERE "name" = OLD."username";
    END;
CREATE TRIGGER "Individual_Update"
    BEFORE UPDATE ON "Individual"
    BEGIN
        UPDATE "User"
            SET "name" = NEW."username"
            WHERE "name" = OLD."username";
    END;

CREATE TABLE "RealEstate" (
    "name" TEXT PRIMARY KEY NOT NULL REFERENCES "User" ON DELETE CASCADE ON UPDATE CASCADE,
    "expireTime" INTEGER DEFAULT 0
);
CREATE TRIGGER "RealEstate_Creation"
    BEFORE INSERT ON "RealEstate"
    BEGIN
        INSERT INTO "User"
            VALUES (NEW."name");
    END;
CREATE TRIGGER "RealEstate_Delete"
    BEFORE DELETE ON "RealEstate"
    BEGIN
        DELETE FROM "User"
            WHERE "name" = OLD."name";
    END;
CREATE TRIGGER "RealEstate_Update"
    BEFORE UPDATE ON "RealEstate"
    BEGIN
        UPDATE "User"
            SET "name" = NEW."name"
            WHERE "name" = OLD."name";
    END;
INSERT INTO "RealEstate" ("name")
    VALUES ("acm");

CREATE TABLE "House" (
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
    "toDelete" BOOLEAN DEFAULT 0,
    PRIMARY KEY ("houseId", "ownerId"),
    FOREIGN KEY ("ownerId") REFERENCES "User" ("name")
);

CREATE TABLE "PaidHouses" (
    "individualId" TEXT NOT NULL REFERENCES "Individual" ON DELETE CASCADE ON UPDATE CASCADE,
    "ownerId" TEXT NOT NULL,
    "houseId" TEXT NOT NULL,
    PRIMARY KEY ("individualId", "ownerId", "houseId"),
    FOREIGN KEY ("houseId", "ownerId") REFERENCES "House" ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO "Individual" ("username","displayName","balance","isAdmin","passwordHash","passwordSalt")
    VALUES ("behnam","بهنام همایون","200", FALSE, "UM6RigwvZ2GbE5G5+fplRYcMHvUG38grN6lJmPfjanQ=", "smCGaStCKSYRr1aRSRdEEboMa4ectuC5lP1y42/nCVw=");
INSERT INTO "Individual" ("username","displayName","balance","isAdmin","passwordHash","passwordSalt")
    VALUES ("admin","ادمین","1000", TRUE, "UM6RigwvZ2GbE5G5+fplRYcMHvUG38grN6lJmPfjanQ=", "smCGaStCKSYRr1aRSRdEEboMa4ectuC5lP1y42/nCVw=");
REPLACE INTO "House" ("houseId","ownerId","area","imageUrl","address","phone","description","buildingType","dealType","priceBase","priceRent","priceSell")
    VALUES ('sdfghjkcxjhjkojhlkmknzvnsdjlkfalkdnamxjamoddmodm','behnam','100','','UT','09123456789','?!!','VILLA','SELL',0,0,'100');
INSERT INTO "PaidHouses" ("individualId","ownerId","houseId")
    VALUES ("behnam","behnam","sdfghjkcxjhjkojhlkmknzvnsdjlkfalkdnamxjamoddmodm");
