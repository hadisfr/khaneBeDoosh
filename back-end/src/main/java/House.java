package main.java;

import java.io.IOException;
import java.sql.SQLException;

public class House {

    protected HouseDetail detail;
    protected User owner;
    protected String ownerName;
    protected String imageUrl;
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
            if (this.owner instanceof RealEstate)
                return ((RealEstate)(owner)).getHouse(this.id).detail;
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

    public String getAddress() throws IOException, SQLException, ClassNotFoundException {
        return this.getDetail().getAddress();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner, Price price) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.owner = owner;
        this.ownerName = owner.getUsername();
        this.price = price;
        detail = null;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                 String address, String phone, String description, Price price) {
        this(id, area, buildingType, imageUrl, owner, price);
        this.detail = new HouseDetail(address, phone, description);
    }

    public Price getPrice() {
        return price;
    }
}