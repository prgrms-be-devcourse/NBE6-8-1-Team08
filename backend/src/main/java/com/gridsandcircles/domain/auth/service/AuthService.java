package com.gridsandcircles.domain.auth.service;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.gridsandcircles.domain.admin.admin.service.AdminService;
import com.gridsandcircles.domain.auth.dto.LoginResponseDto;
import com.gridsandcircles.domain.auth.dto.RefreshTokenResponseDto;
import com.gridsandcircles.domain.auth.entity.RefreshToken;
import com.gridsandcircles.domain.auth.repository.RefreshTokenRepository;
import com.gridsandcircles.domain.auth.util.JwtUtil;
import com.gridsandcircles.global.ServiceException;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final AdminService adminService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Transactional
  public LoginResponseDto loginAdmin(String adminId, String password) {
    if (!passwordEncoder.matches(password, adminService.getAdmin(adminId).getPassword())) {
      throw new ServiceException(UNAUTHORIZED, "Password does not match");
    }

    return new LoginResponseDto(adminId, jwtUtil.generateToken(adminId),
        createRefreshToken(adminId));
  }

  @Transactional
  public void deleteRefreshToken(String adminId) {
    refreshTokenRepository.deleteById(adminId);
  }

  @Transactional
  public RefreshTokenResponseDto refreshTokens(
      String expiredAccessToken,
      String originalRefreshToken
  ) {
    String adminId = jwtUtil.getAdminIdFromExpiredToken(expiredAccessToken);

    RefreshToken refreshTokenEntity = refreshTokenRepository.findById(adminId)
        .orElseThrow(() -> new NoSuchElementException("Refresh token not found for administrator"));

    if (!refreshTokenEntity.getRefreshToken().equals(originalRefreshToken)) {
      throw new ServiceException(UNAUTHORIZED, "Refresh token does not match");
    }

    deleteRefreshToken(adminId);

    return new RefreshTokenResponseDto(jwtUtil.generateToken(adminId), createRefreshToken(adminId));
  }

  private String createRefreshToken(String adminId) {
    return refreshTokenRepository.save(new RefreshToken(adminId, UUID.randomUUID().toString()))
        .getRefreshToken();
  }
}
