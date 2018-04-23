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
}
