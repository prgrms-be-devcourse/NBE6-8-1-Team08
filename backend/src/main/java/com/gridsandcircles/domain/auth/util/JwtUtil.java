package com.gridsandcircles.domain.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  @Value("${JWT_SECRET}")
  private String jwtSecret;

  @Value("${TOKEN_EXPIRE_TIME}")
  private long tokenExpireTime;

  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}
