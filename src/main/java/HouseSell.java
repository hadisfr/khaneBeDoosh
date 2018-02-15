package main.java;

public class HouseSell extends House {

    private int sellPrice;

    public HouseSell(String id, int area, BuildingType buildingType, String imageUrl, User owner, int sellPrice) {
        super(id, area, buildingType, imageUrl, owner);
        this.sellPrice = sellPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public DealType getDealType() {
        return DealType.BUY;
    }
}
