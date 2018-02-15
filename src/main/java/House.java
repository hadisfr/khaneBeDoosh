package main.java;

public abstract class House{

    private HouseDetail detail;
    private User owner;

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl;
    private String id;
    private int area;
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

    public abstract DealType getDealType();

    private void getDetailFromOwner(){
        this.detail = owner.getHouse(this.id).detail;
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


    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.owner = owner;
        detail = null;
    }

}