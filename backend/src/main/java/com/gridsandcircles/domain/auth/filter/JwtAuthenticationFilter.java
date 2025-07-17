package com.gridsandcircles.domain.auth.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.gridsandcircles.domain.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = null;

    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
    }

    if (token != null) {
      try {
        Claims claims = jwtUtil.getClaims(token);
        String username = claims.getSubject();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
            null, null);
        auth.setDetails(claims);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (ExpiredJwtException e) {
        writeJwtErrorResponse(response, "Access token expired");
        return;
      } catch (JwtException | IllegalArgumentException e) {
        writeJwtErrorResponse(response, "Access token invalid");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void writeJwtErrorResponse(
      HttpServletResponse response,
      String message
  ) throws IOException {
    response.setStatus(SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("""
        {
          "message": "%s"
        }
        """.formatted(message));
  }
}
