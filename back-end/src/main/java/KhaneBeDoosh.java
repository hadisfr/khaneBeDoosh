package main.java;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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

    private HashMap<String, RealEstate> realEstates = new HashMap<String, RealEstate>();
    private HashMap<String, House> houses = new HashMap<String, House>();

    private static final String bankAPIKey = "a1965d20-1280-11e8-87b4-496f79ef1988";
    private static final String bankUri = "http://139.59.151.5:6664/bank/pay";
    public static final String dbUri;
    private static final Logger logger = Logger.getLogger(KhaneBeDoosh.class.getName());

    static {
        dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
                "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));
    }

    private static final String defaultUserUsername = "behnam";

    private KhaneBeDoosh() {
        Logger logger = Logger.getLogger(KhaneBeDoosh.class.getName());
        logger.info("Start KhaneBeDoosh");

        realEstates.put(RealEstateAcm.getInstance().getUsername(), RealEstateAcm.getInstance());
    }

    public Individual getDefaultUser() throws SQLException, ClassNotFoundException {
        return (Individual) (getUserById(defaultUserUsername));
    }

    public boolean increaseBalance(Individual user, int amount)
            throws IOException, IllegalArgumentException, SQLException, ClassNotFoundException {
        if (amount < 0)
            throw new IllegalArgumentException("Negative Amount");
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(bankUri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("apiKey", bankAPIKey);
        String body = "{\"userId\": " + getDefaultUser().getUsername() + ", \"value\": \"" + amount + "\"}";
        request.setEntity(new StringEntity(body));
        HttpResponse response = client.execute(request);
        String json = IOUtils.toString(response.getEntity().getContent());
        JSONObject object = new JSONObject(json);
//        boolean payResult = object.getBoolean("success");
        boolean payResult = object.getString("result").equals("OK");
        if (payResult) {
            user.setBalance(user.getBalance() + amount);
            IndividualMapper.update(user);
        }
        return payResult;
    }

    private ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice) {
        return Utility.filterHouses((new ArrayList<House>(houses.values())),
                buildingType, dealType, minArea, maxPrice);
    }

    public ArrayList<House> filterHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice)
            throws IOException {
        ArrayList<House> result = new ArrayList<House>(this.searchHouses(buildingType, dealType, minArea, maxPrice));
        for (RealEstate realEstate : realEstates.values()) {
            result.addAll(realEstate.searchHouses(buildingType, dealType, minArea, maxPrice));
        }
        return result;
    }

    boolean isUserRealEstate(String name) {
        return realEstates.containsKey(name);
    }

    boolean isUserRealEstate(User user) {
        return user instanceof RealEstate;
    }

    public House getHouseById(String houseId, String userId) throws IOException, SQLException, ClassNotFoundException {
        return HouseMapper.getHouseById(houseId, userId);
    }

    public User getUserById(String username) throws SQLException, ClassNotFoundException {
        if (isUserRealEstate(username))
            return realEstates.get(username);
        else
            return IndividualMapper.getByUsername(username);
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int sellPrice,
                         String address, String phone, String description)
            throws SQLException, IOException, ClassNotFoundException {
        HouseMapper.insertHouse(new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, new PriceSell(sellPrice)));
    }

    public void addHouse(String id, int area, BuildingType buildingType, String imageUrl, User owner,
                         int rentPrice, int basePrice,
                         String address, String phone, String description)
            throws SQLException, IOException, ClassNotFoundException {
        HouseMapper.insertHouse(new House(id, area, buildingType, imageUrl, owner, address,
                phone, description, new PriceRent(rentPrice, basePrice)));
    }
}