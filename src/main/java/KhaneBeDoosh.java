package main.java;

import java.util.ArrayList;

public class KhaneBeDoosh {

    ArrayList<User> users = new ArrayList<User>();

    private static KhaneBeDoosh khaneBedoosh = new KhaneBeDoosh();

    private KhaneBeDoosh(){}

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

    public void chargeBalance(int userID, int amount){
        if(amount < 0)
            amount = 0;
        for (User user : users){
            if(!(user instanceof Individual))
                continue;
            Individual individual = (Individual) user;
            if(userID == individual.getId()){
                individual.setBalance(individual.getBalance() + amount);
            }
        }
    }

}