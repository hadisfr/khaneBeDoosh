package main.java;

public class HouseRent extends House {

    private int rentPrice;
    private int basePrice;

    public HouseRent(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                     int rentPrice, int basePrice) {
        super(id, area, buildingType, imageUrl, owner);
        setRentPrice(rentPrice);
        setBasePrice(basePrice);
    }

    public HouseRent(String id, int area, BuildingType buildingType, String imageUrl, User owner,
            int rentPrice, int basePrice, String address, String phone, String description, String expireTime) {
        super(id, area, buildingType, imageUrl, owner, address, phone, description, expireTime);
        setRentPrice(rentPrice);
        setBasePrice(basePrice);
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    private void setRentPrice(int rentPrice) {
        if(rentPrice < 0)
            rentPrice = 0;
        this.rentPrice = rentPrice;
    }

    private void setBasePrice(int basePrice) {
        if(basePrice < 0)
            basePrice = 0;
        this.basePrice = basePrice;
    }

    @Override
    public DealType getDealType() {
        return DealType.RENT;
    }
}
