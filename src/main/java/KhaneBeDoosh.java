package main.java;

import java.util.ArrayList;

public class KhaneBeDoosh {

    private ArrayList<User> users = new ArrayList<User>();
    private static KhaneBeDoosh khaneBedoosh = new KhaneBeDoosh();

    private KhaneBeDoosh(){
        Individual individual = new Individual("بهنام همایون", 200, "09123456789", "behnam", "p@sw00rd");
        users.add(individual);
        addHouse("398y2iuwjndwksfsd", 200, BuildingType.APARTMENT, "http://google.com",
                individual, 100, 200, "address", "09123456789", "description", "2038-02-12");
        addHouse("roshanpazhooh", 800, BuildingType.VILLA, "http://yahoo.com",
                individual, 90, "address", "09123456789", "description", "2038-02-12");
        addHouse("amoopoorang", 800, BuildingType.APARTMENT, "http://bing.com",
                individual, 90000, "address", "09123456789", "description", "2038-02-12");
    }

    public User getDefaultUser() {
        return users.get(0);
    }

    public static KhaneBeDoosh getInstance(){
        return khaneBedoosh;
    }

    public ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice){
        ArrayList<House> result = new ArrayList<House>();
        for (User user : users){
            result.addAll(user.filterHouses(buildingType, dealType, minArea, maxPrice));
        }
        return result;
    }

    public void chargeBalance(int userId, int amount){
        if(amount < 0)
            amount = 0;
        for (User user : users){
            if(!(user instanceof Individual))
                continue;
            Individual individual = (Individual) user;
            if(userId == individual.getId()){
                individual.setBalance(individual.getBalance() + amount);
            }
        }
    }

    public House getHouseById(String houseId, int userId) {
        return getUserById(userId).getHouse(houseId);
    }

    private static int userIdBase = 1000;
    private User getUserById(int userId) {
        return users.get(userId - userIdBase);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner, int sellPrice){
        HouseSell house = new HouseSell(id,area, buildingType, imageUrl, owner, sellPrice);
        if(owner instanceof Individual)
            ((Individual)owner).addHouse(house);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner, int sellPrice,
            String address, String phone, String description, String expireTime){
        HouseSell house = new HouseSell(id,area, buildingType, imageUrl, owner, sellPrice, address,
            phone, description, expireTime);
        if(owner instanceof Individual)
            ((Individual)owner).addHouse(house);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int rentPrice, int basePrice){
        HouseRent house = new HouseRent(id,area, buildingType, imageUrl, owner, rentPrice, basePrice);
        if(owner instanceof Individual)
            ((Individual)owner).addHouse(house);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
            int rentPrice, int basePrice, String address, String phone, String description, String expireTime){
        HouseRent house = new HouseRent(id,area, buildingType, imageUrl, owner, rentPrice, basePrice, address,
            phone, description, expireTime);
        if(owner instanceof Individual)
            ((Individual)owner).addHouse(house);
    }
}