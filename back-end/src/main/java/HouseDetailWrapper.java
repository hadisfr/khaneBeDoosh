package main.java;

public class HouseDetailWrapper {
    private String imageUrl;
    private String id;
    private int area;
    private BuildingType buildingType;
    private DealType dealType;
    private Price price;
    private String address;
    private String description;
    private String expireTime;

    public HouseDetailWrapper(House house) {
        this.dealType = house.getDealType();
        this.imageUrl = house.getImageUrl();
        this.id = Utility.encryptHouseId(house.getId(), house.getOwner().getId());
        this.area = house.getArea();
        this.buildingType = house.getBuildingType();
        this.price = house.getPrice();
        if (this.imageUrl.equals(""))
            this.imageUrl = KhaneBeDoosh.getNoPicUri();
        this.address = house.getAddress();
        this.description = house.getDescription();
        this.expireTime = house.getExpireTime();
    }
}
