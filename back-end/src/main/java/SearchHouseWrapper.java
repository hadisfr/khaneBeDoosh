package main.java;

public class SearchHouseWrapper {

    private String imageUrl;
    private String id;
    private int area;
    private BuildingType buildingType;
    private DealType dealType;
    private Price price;

    public SearchHouseWrapper(House house) {
        this.dealType = house.getDealType();
        this.imageUrl = house.getImageUrl();
        this.id = Utility.encrypt(house.getId(), house.getOwner().getId());
        this.area = house.getArea();
        this.buildingType = house.getBuildingType();
        this.price = house.getPrice();
        if (this.imageUrl.equals(""))
            this.imageUrl = KhaneBeDoosh.getNoPicUri();
    }
}
