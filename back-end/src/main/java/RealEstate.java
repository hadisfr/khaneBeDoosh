package main.java;

import java.io.IOException;
import java.util.ArrayList;

public abstract class RealEstate extends User {

    protected String uri;
    protected long lastTimesatmp;

    public RealEstate(String username, String uri) {
        super(username);
        this.uri = uri;
        this.lastTimesatmp = 0;
    }

    public abstract ArrayList<House> getHouses() throws IOException;

    public abstract House getHouse(String id) throws IOException;

    public ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice) throws IOException {
        return Utility.filterHouses(getHouses(), buildingType, dealType, minArea, maxPrice);
    }

    public long getLastTimesatmp() {
        return lastTimesatmp;
    }
}