package main.java;

public class HouseRent extends House {

    private int rentPrice;
    private int basePrice;

    public HouseRent(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                     int rentPrice, int basePrice) {
        super(id, area, buildingType, imageUrl, owner);
        if(basePrice < 0)
            basePrice = 0;
        if(rentPrice < 0)
            rentPrice = 0;
        this.basePrice = basePrice;
        this.rentPrice = rentPrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    @Override
    public DealType getDealType() {
        return DealType.RENT;
    }
}
