package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;

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

    private HashMap<String, Individual> individuals = new HashMap<String, Individual>();
    private HashMap<String, RealEstate> realEstates = new HashMap<String, RealEstate>();
    private HashMap<String, House> houses = new HashMap<String, House>();

    private static final String bankAPIKey = "a1965d20-1280-11e8-87b4-496f79ef1988";
    private static final String bankUri = "http://acm.ut.ac.ir/ieBank/pay";
    public static final String dbUri;
    static {
        dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
                "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));
    }
    private static final String defaultUserUsername = "behnam";
    private String log = "";

    private KhaneBeDoosh() {
        Logger logger = Logger.getLogger(KhaneBeDoosh.class.getName());
        logger.info("Start KhaneBeDoosh");

        try {
//            IndividualMapper.insert(new Individual("behnam", 200, "بهنام همایون"));
            individuals.put(defaultUserUsername, IndividualMapper.getByUsername(defaultUserUsername));
        } catch (Exception e) {
            this.addLog(e.getMessage());
            logger.severe("EXC: " + e.getMessage());
            e.printStackTrace();
        }
        realEstates.put(RealEstateAcm.getInstance().getUsername(), RealEstateAcm.getInstance());
    }

    public void addLog(String str) {
        this.log += str;
        this.log += "\n";
    }

    public String getLog() {
        return log;
    }

    public Individual getDefaultUser() {
        return individuals.get(defaultUserUsername);
    }

    public boolean increaseBalance(Individual user, int amount) throws IOException, IllegalArgumentException {
        if (amount < 0)
            throw new IllegalArgumentException("Negative Amount");
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(bankUri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("apiKey", bankAPIKey);
        String body = "{\"userId\": " + getDefaultUser().getUsername() + ", \"value\": \"" + amount + "\"}";
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
        for (User user : individuals.values()) {
            result.addAll(((RealEstate) user).searchHouses(buildingType, dealType, minArea, maxPrice));
        }
        for (User user : realEstates.values()) {
            result.addAll(this.searchHouses(buildingType, dealType, minArea, maxPrice));
        }
        return result;
    }

    public House getHouseById(String houseId, String userId) {
        User user = getUserById(userId);
        if (user instanceof Individual)
            return houses.get(houseId);
        else
            return ((RealEstate) user).getHouse(houseId);
    }

    public User getUserById(String username) {
        if (realEstates.containsKey(username))
            return realEstates.get(username);
        else
            return individuals.get(username);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int sellPrice,
                         String address, String phone, String description) {
        House house = new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, new PriceSell(sellPrice));
        if (owner instanceof Individual)
            houses.put(house.getId(), house);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int rentPrice, int basePrice,
                         String address, String phone, String description) {
        House house = new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, new PriceRent(rentPrice, basePrice));
        if (owner instanceof Individual)
            houses.put(house.getId(), house);
    }
}