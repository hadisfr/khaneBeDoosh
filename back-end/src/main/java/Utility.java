package main.java;

import io.jsonwebtoken.*;

import java.util.ArrayList;


public class Utility {
    private Utility() {
    }

    private static final String secret = "Shahrbaraaz";
    public static final int IllegalSearchValue = -1;

    public static String encryptHouseId(String houseId, String ownerId) {
        return Jwts.builder()
                .claim("houseId", houseId)
                .claim("ownerId", ownerId)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public static StringStringPair decryptHouseId(String token) throws IllegalArgumentException {
        try {
            Claims claim = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();
            return new StringStringPair(claim.get("ownerId").toString(), claim.get("houseId").toString());
        } catch (JwtException e) {
            throw new IllegalArgumentException("invalid id");
        }
    }

    public static ArrayList<House> filterHouses(
            ArrayList<House> houses, BuildingType buildingType, DealType dealType, int minArea, Price maxPrice
    ) {
        ArrayList<House> result = new ArrayList<House>();
        for (House house : houses) {
            Price price = house.getPrice();
            boolean priceCheck = false;
            if (maxPrice != null) {
                if (price instanceof PriceSell && maxPrice instanceof PriceSell) {
                    priceCheck = ((PriceSell) price).getSellPrice() >= ((PriceSell) maxPrice).getSellPrice();
                } else if (price instanceof PriceRent && maxPrice instanceof PriceRent) {
                    int basePrice = ((PriceRent) maxPrice).getBasePrice();
                    int rentPrice = ((PriceRent) maxPrice).getRentPrice();
                    priceCheck = (basePrice != IllegalSearchValue && ((PriceRent) price).getBasePrice() >= basePrice)
                            || (rentPrice != IllegalSearchValue && ((PriceRent) price).getRentPrice() >= rentPrice);
                }
            }

            if (!((buildingType != null && !house.getBuildingType().equals(buildingType)) ||
                    (dealType != null && !house.getDealType().equals(dealType)) ||
                    (minArea != IllegalSearchValue && house.getArea() < minArea) ||
                    (priceCheck)))
                result.add(house);
        }
        return result;
    }
}
