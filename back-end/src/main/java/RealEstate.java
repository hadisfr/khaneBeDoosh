package main.java;

import java.util.ArrayList;

public abstract class RealEstate extends User {

    protected String uri;

    public RealEstate(String name, String uri) {
        super(name);
        this.uri = uri;
    }

    public abstract ArrayList<House> getHouses();

    public ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice) {
        return filterHouses(getHouses(), buildingType, dealType, minArea, maxPrice);
    }

}