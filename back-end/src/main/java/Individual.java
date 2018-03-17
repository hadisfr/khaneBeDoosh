package main.java;

import java.util.ArrayList;
import java.util.HashMap;

public class Individual extends User {

    private String phone;
    private int balance;
    private String username;
    private String password;
    private transient HashMap<String, House> houses = new HashMap<String, House>();

    private transient ArrayList<IntStringPair> paidHouses = new ArrayList<IntStringPair>();

    public Individual(String name, int balance, String phone, String username, String password) {
        super(name);
        if (balance < 0)
            balance = 0;
        this.balance = balance;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public boolean hasPaidforHouse(String houseId, int ownerId) {
        for (IntStringPair member : paidHouses) {
            if (member.equals(new IntStringPair(ownerId, houseId))) {
                return true;
            }
        }
        return false;
    }

    private static int phonePrice = 1000;

    public boolean payForHouse(String houseId, int ownerId) {
        if (balance >= phonePrice) {
            paidHouses.add(new IntStringPair(ownerId, houseId));
            balance -= phonePrice;
            return true;
        }
        return false;
    }

    public void addHouse(House house) {
        String id = house.getId();
        houses.put(id, house);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0)
            balance = 0;
        this.balance = balance;
    }

    public ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice) {
        ArrayList<House> hashmapArrayListed = new ArrayList<House>();
        hashmapArrayListed.addAll(houses.values());
        return filterHouses(hashmapArrayListed, buildingType, dealType, minArea, maxPrice);
    }

    public House getHouse(String id) {
        return houses.get(id);
    }
}