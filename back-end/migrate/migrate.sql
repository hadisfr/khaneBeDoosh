PRAGMA foreign_keys = ON;

CREATE TABLE "User" (
    "name" TEXT PRIMARY KEY NOT NULL
);

CREATE TABLE "Individual" (
    "username" TEXT PRIMARY KEY NOT NULL REFERENCES "User",
    "displayName" TEXT,
    "balance" INTEGER DEFAULT 0
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
    "name" TEXT PRIMARY KEY NOT NULL REFERENCES "User",
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
    PRIMARY KEY ("houseId", "ownerId"),
    FOREIGN KEY ("ownerId") REFERENCES "User" ("name")
);

CREATE TABLE "PaidHouses" (
    "individualId" TEXT NOT NULL REFERENCES "Individual",
    "ownerId" TEXT NOT NULL,
    "houseId" TEXT NOT NULL,
    PRIMARY KEY ("individualId", "ownerId", "houseId"),
    FOREIGN KEY ("houseId", "ownerId") REFERENCES "House" ON DELETE CASCADE
);

INSERT INTO "Individual" ("username","balance","displayName")
    VALUES ("behnam","بهنام همایون","200");
INSERT INTO "House" ("houseId","ownerId","area","imageUrl","address","phone","description","buildingType","dealType","priceBase","priceRent","priceSell")
    VALUES ('sdfghjkcxjhjkojhlkmknzvnsdjlkfalkdnamxjamoddmodm','behnam','100','','UT','09123456789','?!!','VILLA','SELL',0,0,'100');
INSERT INTO "PaidHouses" ("individualId","ownerId","houseId")
    VALUES ("behnam","behnam","sdfghjkcxjhjkojhlkmknzvnsdjlkfalkdnamxjamoddmodm");
