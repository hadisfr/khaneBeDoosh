package main.java;

import java.security.Key;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utility {
    private static final String privateKey = "Allahisalivemofo";
    private static final String delimiter = "_";
    public static final int illegalSearchValue = -1;

    public static String encrypt(String houseId, int userId) {
//        String text = houseId + delimiter + userId;
//        Key aesKey = new SecretKeySpec(privateKey.getBytes(), "AES");
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//            byte[] encrypted = cipher.doFinal(text.getBytes());
//            return new String(encrypted);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
        return houseId + delimiter + userId;
    }

    public static IntStringPair decrypt(String text) {
//        Key aesKey = new SecretKeySpec(privateKey.getBytes(), "AES");
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, aesKey);
//            String decrypted = new String(cipher.doFinal(text.getBytes()));
//            String[] decrypted_parts = decrypted.split(delimiter);
//            return new IntStringPair(Integer.parseInt(decrypted_parts[1]), decrypted_parts[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
        String[] decrypted_parts = text.split(delimiter);
        return new IntStringPair(Integer.parseInt(decrypted_parts[1]), decrypted_parts[0]);
    }

    public static ArrayList<House> filterHouses(ArrayList<House> houses, BuildingType buildingType, DealType dealType, int minArea, Price maxPrice) {
        ArrayList<House> result = new ArrayList<House>();
        for (House house : houses) {
            Price price = house.getPrice();
            boolean priceCheck = false;
            if(maxPrice != null) {
                if (price instanceof PriceSell && maxPrice instanceof PriceSell) {
                    priceCheck = ((PriceSell) price).getPrice() >= ((PriceSell) maxPrice).getPrice();
                } else if (price instanceof PriceRent && maxPrice instanceof PriceRent) {
                    int basePrice = ((PriceRent) maxPrice).getBasePrice();
                    int rentPrice = ((PriceRent) maxPrice).getRentPrice();
                    priceCheck = (basePrice != illegalSearchValue &&  ((PriceRent) price).getBasePrice() >= basePrice)
                            || (rentPrice != illegalSearchValue && ((PriceRent) price).getRentPrice() >= rentPrice);
                }
            }

            if (!((buildingType != null && !house.getBuildingType().equals(buildingType)) ||
                    (dealType != null && !house.getDealType().equals(dealType)) ||
                    (minArea != illegalSearchValue && house.getArea() < minArea) ||
                    (priceCheck)))
                result.add(house);
        }
        return result;
    }
}
