package main.java;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utility {
    private static final String privateKey = "Allah is alive!";
    private static final String delimiter = "!@#deli";

    public static String encrypt(String houseId, int userId) {
        String text = houseId + delimiter + userId;
        Key aesKey = new SecretKeySpec(privateKey.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IntStringPair decrypt(String text) {
        Key aesKey = new SecretKeySpec(privateKey.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(text.getBytes()));
            String[] decrypted_parts = decrypted.split(delimiter);
            return new IntStringPair(Integer.parseInt(decrypted_parts[1]), decrypted_parts[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
