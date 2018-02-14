package main.java;

public class House{

    private HouseDetail detail;
    private User owner;

    public String getImageUrl() {
        return imageUrl;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    private String imageUrl;
    private String id;
    private int rentPrice;
    private int basePrice;
    private int sellPrice;
    private int area;
    private DealType dealType;
    private BuildingType buildingType;

    public String getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }


    public DealType getDealType() {
        return dealType;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    private void getDetailFromOwner(){
        this.detail = owner.getHouse(this.id).getDetail();
    }

    public String getPhone() {
        if(detail == null){
            getDetailFromOwner();
        }
        String phone = detail.getPhone();

        return phone;
    }

    public String getDescription(){
        if(detail == null){
            getDetailFromOwner();
        }
        String description = detail.getDescription();

        return description;
    }

    public String getExpireTime() {
        if(detail == null){
            getDetailFromOwner();
        }
        String expireTime = detail.getExpireTime();
        return expireTime;
    }

    public String getAddress() {
        if(detail == null){
            getDetailFromOwner();
        }
        String address = detail.getAddress();
        return address;
    }

    public HouseDetail getDetail(){
        return detail;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, DealType dealType, int basePrice, int rentPrice, int sellPrice, User owner) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.dealType = dealType;
        this.basePrice = basePrice;
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
        this.owner = owner;
        detail = null;
    }

}