package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class KhaneBeDoosh {
    private static KhaneBeDoosh khaneBedoosh = new KhaneBeDoosh();

    public static KhaneBeDoosh getInstance() {
        return khaneBedoosh;
    }

    private ArrayList<User> users = new ArrayList<User>();
    private HashMap<String, House> houses = new HashMap<String, House>();

    private static final String bankAPIKey = "a1965d20-1280-11e8-87b4-496f79ef1988";
    private static final String bankUri = "http://acm.ut.ac.ir/ieBank/pay";
    private static final String noPicUri = "http://localhost:8080/khaneBeDoosh/pics/no-pic.jpg";

    private KhaneBeDoosh() {
        Individual individual = new Individual(
                "بهنام همایون",
                200,
                "09123456789",
                "behnam",
                "p@sw00rd"
        );
        users.add(individual);
        users.add(RealEstateAcm.getInstance());
    }

    public User getDefaultUser() {
        return users.get(0);
    }

    public static String getNoPicUri() {
        return noPicUri;
    }

    public boolean increaseBalance(Individual user, int amount) throws IOException, IllegalArgumentException {
        if (amount < 0)
            throw new IllegalArgumentException("Negative Amount");
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
            if (response != null)
                json = IOUtils.toString(response.getEntity().getContent());
            else
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(json);
        boolean payResult = object.getBoolean("success");
        if (payResult)
            user.setBalance(user.getBalance() + amount);
        return payResult;
    }

    private ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice) {
        return Utility.filterHouses((new ArrayList<House>(houses.values())),
                buildingType, dealType, minArea, maxPrice);
    }

    public ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice) {
        ArrayList<House> result = new ArrayList<House>();
        for (User user : users) {
            if (!(user instanceof Individual))
                result.addAll(((RealEstate) user).searchHouses(buildingType, dealType, minArea, maxPrice));
            else
                result.addAll(this.searchHouses(buildingType, dealType, minArea, maxPrice));
        }
        return result;
    }

    public House getHouseById(String houseId, int userId) {
        User user = getUserById(userId);
        if (user instanceof Individual)
            return houses.get(houseId);
        else
            return ((RealEstate) user).getHouse(houseId);
    }

    private static final int userIdBase = 1000;

    public User getUserById(int userId) {
        return users.get(userId - userIdBase);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int sellPrice,
                         String address, String phone, String description, String expireTime) {
        House house = new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, expireTime, new PriceSell(sellPrice));
        if (owner instanceof Individual)
            houses.put(house.getId(), house);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int rentPrice, int basePrice,
                         String address, String phone, String description, String expireTime) {
        House house = new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, expireTime, new PriceRent(rentPrice, basePrice));
        if (owner instanceof Individual)
            houses.put(house.getId(), house);
    }
}