package ru.itmo.lab4.util.jwt;

import io.jsonwebtoken.*;

import javax.inject.Inject;
import java.security.Key;
import java.util.Date;

public class JwtUtils {
    private static final SignatureAlgorithm HASH_ALGO = SignatureAlgorithm.HS512;

    @Inject private KeyGenerator keyGenerator;
    private final int jwtExpirationMs = 86400000;


    public String parseJwt(String headerRequest) {
        if (headerRequest != null && !headerRequest.isEmpty() && headerRequest.startsWith("Bearer ")) {
            return headerRequest.substring("Bearer".length()).trim();
        }
        return null;
    }


    public String generateJwtToken(String username, String issuer) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(HASH_ALGO, key)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        Key key = keyGenerator.generateKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }


    public void validateJwtToken(String authToken) {
        Key key = keyGenerator.generateKey();
        Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
    }
}
