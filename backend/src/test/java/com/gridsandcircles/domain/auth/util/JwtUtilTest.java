package com.gridsandcircles.domain.auth.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gridsandcircles.global.ServiceException;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {

  @Autowired
  private JwtUtil jwtUtil;

  private final String testUsername = "test";

  @Test
  @DisplayName("JWT 유틸리티: JWT 생성 성공")
  void jwtUtil_1() {
    assertThat(jwtUtil.generateToken(testUsername)).isNotBlank();
  }

  @Test
  @DisplayName("JWT 유틸리티: JWT 페이로드 파싱")
  void jwtUtil_2() {
    Claims claims = jwtUtil.getClaims(jwtUtil.generateToken(testUsername));

    assertThat(claims.getSubject()).isEqualTo(testUsername);
    assertThat(claims.getExpiration()).isAfter(new Date());
  }

  @Test
  @DisplayName("JWT 유틸리티: 만료된 JWT의 subject 파싱")
  void jwtUtil_3() {
    String expiredToken = jwtUtil
        .generateToken(testUsername, new Date(System.currentTimeMillis() - 1000));

    assertThat(jwtUtil.getAdminIdFromExpiredToken(expiredToken)).isEqualTo(testUsername);
  }

  @Test
  @DisplayName("JWT 유틸리티: 유효한 JWT로 재발급 요청 시 처리 거부")
  void jwtUtil_4() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> jwtUtil.getAdminIdFromExpiredToken(jwtUtil.generateToken(testUsername)));

    assertThat(ex.getMessage()).isEqualTo("Access token not expired");
  }

  @Test
  @DisplayName("JWT 유틸리티: 유효하지 않은 JWT로 재발급 요청 시 처리 거부")
  void jwtUtil_5() {
    assertThrows(ServiceException.class,
        () -> jwtUtil.getAdminIdFromExpiredToken("invalid.token.string"));
  }
}
