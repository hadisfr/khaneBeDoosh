package main.java;

import java.io.IOException;
import java.util.ArrayList;

public abstract class RealEstate extends User {

    protected String uri;
    protected long lastTimestamp;

    public RealEstate(String username, String uri) {
        super(username);
        this.uri = uri;
        this.lastTimestamp = 0;
    }

    public abstract ArrayList<House> getHouses() throws IOException;

    public abstract House getHouse(String id) throws IOException;

    public long getLastTimestamp() {
        return lastTimestamp;
    }
}