package main.java;

import io.jsonwebtoken.*;

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
}
