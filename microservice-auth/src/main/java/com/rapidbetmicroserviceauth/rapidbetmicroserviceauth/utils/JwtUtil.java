package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String sectret = "5225a059699b7325d2f3449b5a232d4aa1c3d7aa1ac4e9b6249e97415a8972eb";
    private final SecretKey secretKey;

    public JwtUtil(){
        byte[] decodedKey = sectret.getBytes(StandardCharsets.UTF_8);
        secretKey = new SecretKeySpec(decodedKey, "HmacSHA512");
    }

    public String generateToken(Long id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 864000000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        Integer intId = (Integer) claims.get("id");
        return intId.longValue();
    }

    public Boolean authorize(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        Integer intId = (Integer) claims.get("id");
        if(intId != null){
            return true;
        }else{
            return false;
        }
    }


}