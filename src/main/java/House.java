package main.java;

public abstract class House{

    protected HouseDetail detail;
    protected User owner;
    protected String imageUrl;
    protected String id;
    protected int area;
    protected BuildingType buildingType;

    public abstract DealType getDealType();

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

    protected void getDetailFromOwner(){
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

    public User getOwner() {
        return owner;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.owner = owner;
        detail = null;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner,
            String address, String phone, String description, String expireTime) {
        this(id, area, buildingType, imageUrl, owner);
        this.detail = new HouseDetail(address, phone, description, expireTime);
    }

}