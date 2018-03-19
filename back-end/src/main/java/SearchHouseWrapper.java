package main.java;

public class SearchHouseWrapper {

    private String imageUrl = KhaneBeDoosh.getNopicUri();
    private String id;
    private int area;
    private BuildingType buildingType;
    private DealType dealType;
    private Price price;

    public SearchHouseWrapper(House house) {
        if(house instanceof HouseSell)
            dealType = DealType.SELL;
        else if(house instanceof HouseRent)
            dealType = DealType.RENT;
        this.imageUrl = house.getImageUrl();
        this.id = Utility.encrypt(house.getId(), house.getOwner().getId());
        this.area = house.getArea();
        this.buildingType = house.getBuildingType();

    }
}
