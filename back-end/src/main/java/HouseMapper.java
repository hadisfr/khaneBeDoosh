package main.java;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class HouseMapper {
    private static final String HouseTableName = "House";
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

    private static final Logger logger = Logger.getLogger(HouseMapper.class.getName());
    private static final String dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
            "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));

    public static House getHouseById(String houseId, String ownerId) throws ClassNotFoundException, SQLException {
        logger.info(String.format("get House(ownerId=%s, houseId=%s) from %s", ownerId, houseId, dbUri));

        // TODO: update database

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

    public static void insertHouse(House house) throws SQLException, ClassNotFoundException, IOException {
        logger.info(String.format("insert House(ownerId=%s, houseId=%s) from %s",
                house.getOwnerName(), house.getId(), dbUri));

        Class.forName("org.sqlite.JDBC");

        boolean isOwnerIndividual = !KhaneBeDoosh.getInstance().isUserRealEstate(house.getOwnerName());
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUri);
            connection.setAutoCommit(false);
            try {
                PreparedStatement stmt = connection.prepareStatement(String.format(
                        "insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
                                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                        HouseTableName, HouseIdKey, OwnerIdKey,
                        AreaKey, ImageUrlKey, AddressKey, PhoneKey, DescriptionKey, BuildingTypeKey, DealTypeKey,
                        PriceBaseKey, PriceRentKey, PriceSellKey
                ));
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
                stmt.setInt(10, house.getDealType() == DealType.SELL ?
                        ((PriceSell) (house.getPrice())).getSellPrice() : 0);
                stmt.executeUpdate();

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
    }
}
