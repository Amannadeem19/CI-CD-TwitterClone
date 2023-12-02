package com.example.TwitterBackend.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private SecretKey key  = Keys.hmacShaKeyFor(JwtConstant.secretKey.getBytes());

    public JwtProvider() {

    }
    public String generateToken(Authentication auth){
        String jwt = Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth)
                .signWith(key)
                .compact();
        return jwt;
    }
    public String getEmailFromToken(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

//        String email = String.valueOf(claims.get("email"));
        return claims.getSubject();
    }



}
