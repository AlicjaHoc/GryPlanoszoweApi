package com.project.boardgames.services;
import com.project.boardgames.entities.AppUser;
import com.project.boardgames.entities.JwtToken;
import com.project.boardgames.repositories.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenService {
    @Autowired
    JwtTokenRepository jwtTokenRepository;
    private static final String SECRET_KEY = "secret";

    public String generateToken(AppUser user) {
        JwtToken existingToken = jwtTokenRepository.findByUser_Id(user.getId());
        if (existingToken != null && existingToken.getToken() != null && !isTokenExpired(existingToken.getToken())) {
            // Token is not expired, return the existing token
            return existingToken.getToken();
        } else {
            // Token has expired or doesn't exist, generate a new token
            return generateNewToken(user);
        }
    }

    private String generateNewToken(AppUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        JwtToken token = new JwtToken();
        token.setValid(true);
        token.setToken(createToken(claims, user.getEmail()));
        token.setUser(user);
        jwtTokenRepository.save(token);
        return token.getToken();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 10); // 10 hours
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, AppUser user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
