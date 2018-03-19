package main.java;

public class House {

    protected HouseDetail detail;
    protected User owner;
    protected String imageUrl = "";
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
        if (imageUrl.equals(""))
            return KhaneBeDoosh.getNopicUri();
        else
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

    protected void getDetailFromOwner() {
        this.detail = ((RealEstate) owner).getHouse(this.id).detail;
    }

    public String getPhone() {
        if (detail == null) {
            getDetailFromOwner();
        }
        String phone = detail.getPhone();

        return phone;
    }

    public String getDescription() {
        if (detail == null) {
            getDetailFromOwner();
        }
        String description = detail.getDescription();

        return description;
    }

    public String getExpireTime() {
        if (detail == null) {
            getDetailFromOwner();
        }
        String expireTime = detail.getExpireTime();
        return expireTime;
    }

    public String getAddress() {
        if (detail == null) {
            getDetailFromOwner();
        }
        String address = detail.getAddress();
        return address;
    }

    public User getOwner() {
        return owner;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner, Price price) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.imageUrl = imageUrl;
        this.owner = owner;
        this.price = price;
        detail = null;
    }

    public House(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                 String address, String phone, String description, String expireTime, Price price) {
        this(id, area, buildingType, imageUrl, owner, price);
        this.detail = new HouseDetail(address, phone, description, expireTime);
    }

    public Price getPrice() {
        return price;
    }
}