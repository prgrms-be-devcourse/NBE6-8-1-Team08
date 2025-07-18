package com.gridsandcircles.domain.auth.service;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.gridsandcircles.domain.admin.admin.entity.Admin;
import com.gridsandcircles.domain.admin.admin.repository.AdminRepository;
import com.gridsandcircles.domain.auth.entity.RefreshToken;
import com.gridsandcircles.domain.auth.repository.RefreshTokenRepository;
import com.gridsandcircles.global.ServiceException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final AdminRepository adminRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;

  public String loginAdmin(String adminId, String password) {
    Admin admin = getAdmin(adminId);

    if (!passwordEncoder.matches(password, admin.getPassword())) {
      throw new ServiceException(UNAUTHORIZED, "Password does not match");
    }

    return adminId;
  }

  public String createRefreshToken(String adminId) {
    return refreshTokenRepository.save(new RefreshToken(adminId, UUID.randomUUID().toString()))
        .getRefreshToken();
  }

  public void deleteRefreshToken(String adminId) {
    refreshTokenRepository.deleteById(adminId);
  }

  private Admin getAdmin(String adminId) {
    Optional<Admin> admin = adminRepository.findById(adminId);

    if (admin.isEmpty()) {
      throw new NoSuchElementException("Admin not found");
    }

    return admin.get();
  }
}
