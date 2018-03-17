package main.java;

import java.util.ArrayList;

public abstract class User {

    protected String name;
    private int id;
    protected static int next_index = 1000;

    public User(String name) {
        this.name = name;
        this.id = next_index++;
    }

    public abstract ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice);

    public abstract House getHouse(String id);

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    protected ArrayList<House> filterHouses(ArrayList<House> houses, BuildingType buildingType, DealType dealType, int minArea, int maxPrice) {
        ArrayList<House> result = new ArrayList<House>();
        for (House house : houses) {
            int price = 0;
            if (house instanceof HouseRent)
                price = ((HouseRent) house).getRentPrice();
            else if (house instanceof HouseSell)
                price = ((HouseSell) house).getSellPrice();
            if (house.getBuildingType().equals(buildingType)
                    && house.getDealType().equals(dealType)
                    && house.getArea() >= minArea
                    && price <= maxPrice
                    )
                result.add(house);
        }
        return result;
    }

}