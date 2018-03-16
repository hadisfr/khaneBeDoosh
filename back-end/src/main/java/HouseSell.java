package main.java;

public class HouseSell extends House {

    private int sellPrice;

    public HouseSell(String id, int area, BuildingType buildingType, String imageUrl, User owner, int sellPrice) {
        super(id, area, buildingType, imageUrl, owner);
        this.setSellPrice(sellPrice);
    }

    public HouseSell(String id, int area, BuildingType buildingType, String imageUrl, User owner, int sellPrice,
            String address, String phone, String description, String expireTime) {
        super(id, area, buildingType, imageUrl, owner, address, phone, description, expireTime);
        this.setSellPrice(sellPrice);
    }

    public int getSellPrice() {
        return sellPrice;
    }

    private void setSellPrice(int sellPrice) {
        if(sellPrice < 0)
            sellPrice = 0;
        this.sellPrice = sellPrice;
    }

    @Override
    public DealType getDealType() {
        return DealType.SELL;
    }
}
