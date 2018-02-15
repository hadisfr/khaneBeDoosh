package main.java;

import java.util.ArrayList;

public abstract class User{

    protected String name;

    public User(String name){
        this.name = name;
    }

    public abstract ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice);
    public abstract House getHouse(String id);
    public String getName() {
        return name;
    }
}