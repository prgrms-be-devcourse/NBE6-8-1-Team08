package com.gridsandcircles.domain.auth.util;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.gridsandcircles.global.ServiceException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

  public String getAdminIdFromExpiredToken(String token) {
    try {
      getClaims(token);
      throw new IllegalArgumentException("Access token not expired");
    } catch (ExpiredJwtException e) {
      return e.getClaims().getSubject();
    } catch (JwtException | IllegalArgumentException e) {
      throw new ServiceException(UNAUTHORIZED, "Access token invalid");
    }
  }
}
