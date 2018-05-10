package main.java;

import io.jsonwebtoken.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class Utility {
    private Utility() {
    }

    private static final String secret = "Shahrbaraaz";
    public static final int IllegalSearchValue = -1;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

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

    public static String getToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        try {
            Claims claim = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();
            return claim.get("username").toString();
        } catch (JwtException e) {
            throw new IllegalArgumentException("invalid token");
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String passwordStr, String saltStr) {
        char[] password = passwordStr.toCharArray();
        byte[] salt = Base64.getDecoder().decode(saltStr);
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashed = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean checkHash(String passwordStr, String salt, String expectedHash) {
        char[] password = passwordStr.toCharArray();
        String pwdHash = hash(passwordStr, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length() != expectedHash.length()) return false;
        return pwdHash.equals(expectedHash);
    }
}
