package main.java;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HouseMapper {
    private static final String HouseTableName = "House";
    private static final String RealEstateTableName = "RealEstate";
    private static final String OwnerIdKey = "ownerId";
    private static final String HouseIdKey = "houseId";
    private static final String AreaKey = "area";
    private static final String BuildingTypeKey = "buildingType";
    private static final String DealTypeKey = "dealType";
    private static final String ImageUrlKey = "imageUrl";
    private static final String AddressKey = "address";
    private static final String PriceSellKey = "priceSell";
    private static final String PriceRentKey = "priceRent";
    private static final String PriceBaseKey = "priceBase";
    private static final String PhoneKey = "phone";
    private static final String DescriptionKey = "description";
    private static final String RealEstateKey = "name";
    private static final String ExpireTimeKey = "expireTime";

    private static final Logger logger = Logger.getLogger(HouseMapper.class.getName());
    private static final String dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
            "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));

    public static House getHouseById(String houseId, String ownerId)
            throws ClassNotFoundException, SQLException, IOException {
        logger.info(String.format("get House(ownerId=%s, houseId=%s) from %s", ownerId, houseId, dbUri));

        if (KhaneBeDoosh.getInstance().isUserRealEstate(ownerId) && isRealEstateOutdated(ownerId))
            updateRealEstate(ownerId);

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        House ret;
        boolean isOwnerIndividual = !KhaneBeDoosh.getInstance().isUserRealEstate(ownerId);
        try {
            connection = DriverManager.getConnection(dbUri);

            PreparedStatement stmt = connection.prepareStatement(String.format(
                    "select * from %s where %s=? and %s=?;", HouseTableName, OwnerIdKey, HouseIdKey));
            stmt.setQueryTimeout(30);
            stmt.setString(1, ownerId);
            stmt.setString(2, houseId);
            ResultSet res = stmt.executeQuery();
            int area = 0;
            BuildingType buildingType = null;
            DealType dealType;
            String imageUrl = "";
            Price price = null;
            String address = "";
            String phone = "";
            String description = "";
            if (res.next()) {
                area = res.getInt(AreaKey);
                imageUrl = res.getString(ImageUrlKey);
                address = res.getString(AddressKey);
                buildingType = BuildingType.parseString(res.getString(BuildingTypeKey));
                dealType = DealType.parseString(res.getString(DealTypeKey));
                if (dealType == DealType.SELL)
                    price = new PriceSell(res.getInt(PriceSellKey));
                else if (dealType == DealType.RENT)
                    price = new PriceRent(res.getInt(PriceBaseKey), res.getInt(PriceRentKey));
                if (isOwnerIndividual) {
                    phone = res.getString(PhoneKey);
                    description = res.getString(DescriptionKey);
                }
            }
            res.close();
            stmt.close();
            if (isOwnerIndividual)
                ret = new House(houseId, area, buildingType, imageUrl, ownerId, address, phone, description, price);
            else
                ret = new House(houseId, area, buildingType, imageUrl, ownerId, address, price);
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    private static boolean isRealEstateOutdated(String name) throws ClassNotFoundException, SQLException {
        logger.info(String.format("check if is outdated RealEstate(name=%s) from %s", name, dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = DriverManager.getConnection(dbUri);
        boolean ret = false;
        try {
            PreparedStatement stmt = connection.prepareStatement(String.format("select * from %s where %s=? and %s <= %s;",
                    RealEstateTableName, RealEstateKey, ExpireTimeKey, "strftime('%s000','now')"));
            stmt.setQueryTimeout(30);
            stmt.setString(1, name);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                ret = true;
            }
            res.close();
            stmt.close();
        } finally {
            if (connection != null)
                connection.close();
        }

        return ret;
    }

    private static void fillStatementWithHouse(House house, PreparedStatement stmt)
            throws SQLException, IOException, ClassNotFoundException {
        boolean isOwnerIndividual = !KhaneBeDoosh.getInstance().isUserRealEstate(house.getOwnerName());
        stmt.setString(1, house.getId());
        stmt.setString(2, house.getOwnerName());
        stmt.setInt(3, house.getArea());
        stmt.setString(4, house.getImageUrl());
        stmt.setString(5, house.getAddress());
        stmt.setString(6, isOwnerIndividual ? house.getPhone() : "");
        stmt.setString(7, isOwnerIndividual ? house.getDescription() : "");
        stmt.setString(8, house.getBuildingType().toString());
        stmt.setString(9, house.getDealType().toString());
        stmt.setInt(10, house.getDealType() == DealType.RENT ?
                ((PriceRent) (house.getPrice())).getBasePrice() : 0);
        stmt.setInt(11, house.getDealType() == DealType.RENT ?
                ((PriceRent) (house.getPrice())).getRentPrice() : 0);
        stmt.setInt(12, house.getDealType() == DealType.SELL ?
                ((PriceSell) (house.getPrice())).getSellPrice() : 0);
    }

    private static PreparedStatement getHouseInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(String.format(
                "insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
                        + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                HouseTableName, HouseIdKey, OwnerIdKey,
                AreaKey, ImageUrlKey, AddressKey, PhoneKey, DescriptionKey, BuildingTypeKey, DealTypeKey,
                PriceBaseKey, PriceRentKey, PriceSellKey
        ));
    }

    public static int insertHouse(House house) throws SQLException, ClassNotFoundException, IOException {
        logger.info(String.format("insert House(ownerId=%s, houseId=%s) from %s",
                house.getOwnerName(), house.getId(), dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        int ret;
        try {
            connection = DriverManager.getConnection(dbUri);
            connection.setAutoCommit(false);
            try {
                PreparedStatement stmt = getHouseInsertStatement(connection);
                fillStatementWithHouse(house, stmt);
                ret = stmt.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
            connection.setAutoCommit(true);
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    public static int updateRealEstate(String realEstateName)
            throws SQLException, ClassNotFoundException, IOException {
        logger.info(String.format("update RealEstate(name=%s) from %s", realEstateName, dbUri));

        Class.forName("org.sqlite.JDBC");

        RealEstate realEstate = (RealEstate) KhaneBeDoosh.getInstance().getUserById(realEstateName);
        ArrayList<House> newHouses = realEstate.getHouses();
        long timestamp = realEstate.getLastTimestamp();

        Connection connection = null;
        int ret = 0;
        try {
            connection = DriverManager.getConnection(dbUri);
            connection.setAutoCommit(false);
            try {
                PreparedStatement stmt = connection.prepareStatement(String.format(
                        "update %s set %s=? where %s=?;", RealEstateTableName, ExpireTimeKey, RealEstateKey));
                stmt.setLong(1, timestamp);
                stmt.setString(2, realEstateName);
                stmt.executeUpdate();

                stmt = connection.prepareStatement(String.format("delete from %s where %s=?;", HouseTableName, OwnerIdKey));
                stmt.setString(1, realEstateName);
                stmt.executeUpdate();
                stmt.close();

                stmt = getHouseInsertStatement(connection);
                for (House house : newHouses) {
                    fillStatementWithHouse(house, stmt);
                    ret += stmt.executeUpdate();
                }
                stmt.close();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
            connection.setAutoCommit(true);
        } finally {
            if (connection != null)
                connection.close();
        }
        return ret;
    }

    public static int updateRealEstates() throws ClassNotFoundException, SQLException, IOException {
        logger.info(String.format("update RealEstates from %s", dbUri));

        Class.forName("org.sqlite.JDBC");

        Connection connection = DriverManager.getConnection(dbUri);
        ArrayList<String> realEstates = new ArrayList<String>();
        try {
            PreparedStatement stmt = connection.prepareStatement(String.format("select %s from %s where %s <= %s;",
                    RealEstateKey, RealEstateTableName, ExpireTimeKey, "strftime('%s000','now')"));
            stmt.setQueryTimeout(30);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                realEstates.add(res.getString(RealEstateKey));
            }
            res.close();
            stmt.close();
        } finally {
            if (connection != null)
                connection.close();
        }

        int ret = 0;
        for (String realEstate : realEstates) {
            ret += updateRealEstate(realEstate);
        }
        return ret;
    }
}
