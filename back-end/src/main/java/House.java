package main.java;

import java.io.IOException;
import java.sql.SQLException;

public class House {

    protected HouseDetail detail;

    protected String ownerName;
    protected String imageUrl;
    protected String address;
    protected String id;
    protected int area;
    protected BuildingType buildingType;

    private Price price;

    public DealType getDealType() {
        if (price instanceof PriceRent)
            return DealType.RENT;
        else if (price instanceof PriceSell)
            return DealType.SELL;
        else
            return null;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    protected HouseDetail getDetail() throws IOException, SQLException, ClassNotFoundException {
        if (this.detail != null)
            return this.detail;
        else {
            User owner = KhaneBeDoosh.getInstance().getUserById(this.ownerName);
            if (owner instanceof RealEstate)
                return ((RealEstate) (owner)).getHouse(this.id).detail;
            else
                return null;
        }
    }

    public String getPhone() throws IOException, SQLException, ClassNotFoundException {
        return this.getDetail().getPhone();
    }

    public String getDescription() throws IOException, SQLException, ClassNotFoundException {
        return this.getDetail().getDescription();
    }

    public String getAddress() {
        return this.address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, String ownerName, Price price, String address) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.ownerName = ownerName;
        this.price = price;
        this.address = address;
        detail = null;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, String ownerName,
                 String address, String phone, String description, Price price) {
        this(id, area, buildingType, imageUrl, ownerName, price, address);
        this.detail = new HouseDetail(phone, description);
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner, Price price, String address) {
        this(id, area, buildingType, imageUrl, owner.getUsername(), price, address);
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                 String address, String phone, String description, Price price) {
        this(id, area, buildingType, imageUrl, owner.getUsername(), address, phone, description, price);
    }

    public Price getPrice() {
        return price;
    }
}