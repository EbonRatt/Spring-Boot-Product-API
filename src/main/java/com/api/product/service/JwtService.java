package com.api.product.service;

import com.api.product.model.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_KEY}")
    private String secretKey;

    public String generateToken(String email){
            Date now = new Date();
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + 3600 * 1000)) // 1 hour
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                    .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        // Compare the extracted username with the username from UserDetails
        return (userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }

}
