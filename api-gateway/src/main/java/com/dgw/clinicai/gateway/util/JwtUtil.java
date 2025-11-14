package com.dgw.clinicai.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${app.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenInvalid(String token) {
        // Jwts.parser() will throw an exception if the token is invalid (e.g., expired, malformed)
        return getAllClaimsFromToken(token).getExpiration() == null;
    }

    public List<String> getRolesFromToken(String token) {
        String roles = getAllClaimsFromToken(token).get("roles", String.class);
        return Arrays.asList(roles.split(","));
    }
}