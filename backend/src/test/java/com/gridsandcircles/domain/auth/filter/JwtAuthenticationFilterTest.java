package com.gridsandcircles.domain.auth.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gridsandcircles.domain.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthenticationFilterTest {

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  private JwtAuthenticationFilter filter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    filter = new JwtAuthenticationFilter(jwtUtil);
    SecurityContextHolder.clearContext();
  }

  @Test
  @DisplayName("JWT 인증 필터: 인증 헤더가 없을 시 인증 없이 필터 체인 정상 진행")
  void noAuthorizationHeader_shouldProceedWithoutAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn(null);

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  @DisplayName("JWT 인증 필터: 인증 헤더에 올바른 값 없을 시 인증 없이 필터 체인 정상 진행")
  void authorizationHeaderWithoutBearer_shouldProceedWithoutAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("No start bearer");

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  @DisplayName("JWT 인증 필터: JWT로 인증 성공 시 Authentication 객체 생성")
  void validToken_shouldAuthenticate() throws Exception {
    String testUsername = "test";
    String token = "example valid token for authentication";
    Claims claims = mock(Claims.class);

    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtUtil.getClaims(token)).thenReturn(claims);
    when(claims.getSubject()).thenReturn(testUsername);

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertEquals(testUsername, SecurityContextHolder.getContext().getAuthentication().getName());
  }

  @Test
  @DisplayName("JWT 인증 필터: 만료된 토큰은 인증 불가")
  void expiredToken_shouldReturn401() throws Exception {
    String token = "expired token";
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtUtil.getClaims(token)).thenThrow(new ExpiredJwtException(null, null, "dummy"));

    StringWriter sw = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    filter.doFilterInternal(request, response, filterChain);

    verify(response).setStatus(SC_UNAUTHORIZED);
    verify(response).setContentType("application/json");
    assertTrue(sw.toString().contains("Access token expired"));

    verify(filterChain, never()).doFilter(request, response);
  }

  @Test
  @DisplayName("JWT 인증 필터: 만료된 토큰은 인증 불가")
  void invalidToken_shouldReturn401() throws Exception {
    String token = "invalid token";
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtUtil.getClaims(token)).thenThrow(new JwtException("invalid"));

    StringWriter sw = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    filter.doFilterInternal(request, response, filterChain);

    verify(response).setStatus(SC_UNAUTHORIZED);
    verify(response).setContentType("application/json");
    assertTrue(sw.toString().contains("Access token invalid"));
    verify(filterChain, never()).doFilter(request, response);
  }
}