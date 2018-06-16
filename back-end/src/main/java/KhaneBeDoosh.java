package main.java;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;

import org.apache.commons.io.IOUtils;
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

    private static final String bankAPIKey = "a1965d20-1280-11e8-87b4-496f79ef1988";
    private static final String bankUri = "http://139.59.151.5:6664/bank/pay";
    public static final String dbUri;
    private static final Logger logger = Logger.getLogger(KhaneBeDoosh.class.getName());

    static {
        dbUri = String.format("jdbc:sqlite:%s", new File(new File(System.getProperty(
                "catalina.base")).getAbsoluteFile(), "webapps/khaneBeDoosh/WEB-INF/khaneBeDoosh.db"));
    }

    private KhaneBeDoosh() {
        Logger logger = Logger.getLogger(KhaneBeDoosh.class.getName());
        logger.info("Start KhaneBeDoosh");

        realEstates.put(RealEstateAcm.getInstance().getUsername(), RealEstateAcm.getInstance());
    }

    public boolean increaseBalance(Individual user, int amount)
            throws IllegalArgumentException, SQLException, ClassNotFoundException, IOException {
        if (amount < 0)
            throw new IllegalArgumentException("Negative Amount");
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(bankUri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("apiKey", bankAPIKey);
        request.setEntity(new StringEntity(String.format("{\"userId\": %s, \"value\": \"%d\"}",
                user.getUsername(), amount)));
        JSONObject response = new JSONObject(IOUtils.toString(client.execute(request).getEntity().getContent()));
//        boolean payResult = response.getBoolean("success");
        boolean payResult = response.getString("result").equals("OK");
        if (payResult) {
            user.setBalance(user.getBalance() + amount);
            IndividualMapper.update(user);
        }
        return payResult;
    }

    public ArrayList<House> searchHouses(BuildingType buildingType, DealType dealType, int minArea, Price maxPrice)
            throws IOException, SQLException, ClassNotFoundException {
        return HouseMapper.searchHouses(buildingType, dealType, minArea, maxPrice);
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

    public ArrayList<Individual> getIndividuals() throws SQLException, ClassNotFoundException {
        return IndividualMapper.getIndividuals();
    }

    public String getToken(String username, String password) throws SQLException, ClassNotFoundException {
        return IndividualMapper.isAuthenticationValid(username, password) ? Utility.getToken(username) : null;
    }
}