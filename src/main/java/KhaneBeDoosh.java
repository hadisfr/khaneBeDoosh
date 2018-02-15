package main.java;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class KhaneBeDoosh {

    private ArrayList<User> users = new ArrayList<User>();
    private static KhaneBeDoosh khaneBedoosh = new KhaneBeDoosh();

    private static String bankAPIKey = "a1965d20-1280-11e8-87b4-496f79ef1988";
    private static String bankUri = "http://acm.ut.ac.ir/ieBank/pay";
    public static String nopicUri = "no-pic.jpg";

    private KhaneBeDoosh(){
        Individual individual = new Individual("بهنام همایون", 200, "09123456789", "behnam", "p@sw00rd");
        users.add(individual);
        users.add(RealEstateAcm.getInstance());
        addHouse("398y2iuwjndwksfsd", 200, BuildingType.APARTMENT, "",
                individual, 100, 200, "address", "09123456789", "description", "2038-02-12");
        addHouse("roshanpazhooh", 800, BuildingType.VILLA, "",
                individual, 90, "address", "09123456789", "description", "2038-02-12");
        addHouse("amoopoorang", 800, BuildingType.APARTMENT, "",
                individual, 90000, "address", "09123456789", "description", "2038-02-12");
    }

    public User getDefaultUser() {
        return users.get(0);
    }

    public static KhaneBeDoosh getInstance(){
        return khaneBedoosh;
    }

    public boolean increaseBalance(Individual user, int amount) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(bankUri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("apiKey", bankAPIKey);
        String body = "{\"userId\": " + getDefaultUser().getId() + ", \"value\": \"" + amount + "\"}";
        request.setEntity(new StringEntity(body));
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            json = IOUtils.toString(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(json);
        boolean payResult = object.getBoolean("success");
        if(payResult)
            user.setBalance(user.getBalance() + amount);
        return payResult;
    }

    public ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, int maxPrice){
        ArrayList<House> result = new ArrayList<House>();
        for (User user : users){
            result.addAll(user.searchHouses(buildingType, dealType, minArea, maxPrice));
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
    public User getUserById(int userId) {
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