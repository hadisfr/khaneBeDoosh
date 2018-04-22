package main.java;

import java.io.IOException;
import java.sql.SQLException;

public class HouseDetailWrapper {
    private String imageUrl;
    private String id;
    private int area;
    private BuildingType buildingType;
    private DealType dealType;
    private Price price;
    private String address;
    private String description;

    public HouseDetailWrapper(House house) throws IOException, SQLException, ClassNotFoundException {
        this.dealType = house.getDealType();
        this.imageUrl = house.getImageUrl();
        this.id = Utility.encryptHouseId(house.getId(), house.getOwnerName());
        this.area = house.getArea();
        this.buildingType = house.getBuildingType();
        this.price = house.getPrice();
        this.address = house.getAddress();
        this.description = house.getDescription();
    }
}
