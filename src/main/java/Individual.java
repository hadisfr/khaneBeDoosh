package main.java;

import java.util.ArrayList;
import java.util.HashMap;

public class Individual extends User{

    private static int counter = 1000;

    String phone;
    int balance;
    int id;
    String username;
    String password;

    protected HashMap<String, House> houses = new HashMap<String, House>();

    public int getId() {
        return id;
    }

    ArrayList<String> paidHouses = new ArrayList<String>();

    public Individual(int balance, String name) {
        super(name);
        if(balance < 0)
            balance = 0;
        this.balance = balance;
        this.id = counter++;
    }

    public boolean hasPaidforHouse(String id){
        for (String member : paidHouses){
            if(member.equals(id)){
                return true;
            }
        }
        return false;
    }

    public void payForHouse(String id){
        paidHouses.add(id);
    }

    public void addHouse(House house){
        String id = house.getId();
        houses.put(id, house);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if(balance < 0)
            balance = 0;
        this.balance = balance;
    }

    public ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice) {
        ArrayList<House> result = new ArrayList<House>();
        for(House house : houses.values()){
            int price = 0;
            if(house instanceof HouseRent)
                price = ((HouseRent)house).getRentPrice();
            else if(house instanceof HouseSell)
                price = ((HouseSell)house).getSellPrice();
            if(house.getBuildingType().equals(buildingType) &&
                    house.getDealType().equals(dealType) &&
                    house.getArea() > minArea &&
                    price < maxPrice)
                result.add(house);
        }
        return result;
    }

    public House getHouse(String id){
        return houses.get(id);
    }
}