package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class RealEstateAcm extends RealEstate {

    private static RealEstateAcm realEstateAcm = new RealEstateAcm();

    public static RealEstateAcm getInstance() {
        return realEstateAcm;
    }

    private RealEstateAcm() {
        super("acm", "http://139.59.151.5:6664/khaneBeDoosh/v2/house");
    }

    @Override
    public ArrayList<House> getHouses() throws IOException {
        ArrayList<House> result = new ArrayList<House>();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
        request.addHeader("accept", "application/json");
        HttpResponse response = client.execute(request);
        String json = IOUtils.toString(response.getEntity().getContent());
        JSONArray array = (new JSONObject(json)).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            String id, imageUrl;
            int area;
            DealType dealType;
            BuildingType buildingType;
            User owner = getInstance();

            JSONObject priceObject = object.getJSONObject("price");

            id = object.getString("id");
            imageUrl = object.getString("imageURL");
            area = object.getInt("area");
            dealType = DealType.parseInt(object.getInt("dealType"));
            buildingType = BuildingType.parseString(object.getString("buildingType"));
            Price price = null;
            if (dealType == DealType.SELL) {
                int sellPrice = priceObject.getInt("sellPrice");
                price = new PriceSell(sellPrice);
            } else if (dealType == DealType.RENT) {
                int rentPrice, basePrice;
                rentPrice = priceObject.getInt("rentPrice");
                basePrice = priceObject.getInt("basePrice");
                price = new PriceRent(basePrice, rentPrice);
            }
            result.add(new House(id, area, buildingType, imageUrl, owner, price));
        }
        return result;
    }

    public House getHouse(String id) throws IOException {
        String houseUri = uri + "/" + id;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(houseUri);
        request.addHeader("accept", "application/json");
        HttpResponse response = client.execute(request);
        String json = IOUtils.toString(response.getEntity().getContent());
        JSONObject object = (new JSONObject(json)).getJSONObject("data");
        String imageUrl, address, phone, description;
        int area;
        DealType dealType;
        BuildingType buildingType;
        User owner = getInstance();

        JSONObject priceObject = object.getJSONObject("price");

        imageUrl = object.getString("imageURL");
        address = object.getString("address");
        phone = object.getString("phone");
        description = object.getString("description");
        area = object.getInt("area");
        dealType = DealType.parseInt(object.getInt("dealType"));
        buildingType = BuildingType.parseString(object.getString("buildingType"));

        Price price = null;
        if (dealType == DealType.SELL) {
            int sellPrice = priceObject.getInt("sellPrice");
            price = new PriceSell(sellPrice);
        } else if (dealType == DealType.RENT) {
            int rentPrice, basePrice;
            rentPrice = priceObject.getInt("rentPrice");
            basePrice = priceObject.getInt("basePrice");
            price = new PriceRent(basePrice, rentPrice);
        }

        return new House(id, area, buildingType, imageUrl, owner, address, phone, description, price);
    }


}