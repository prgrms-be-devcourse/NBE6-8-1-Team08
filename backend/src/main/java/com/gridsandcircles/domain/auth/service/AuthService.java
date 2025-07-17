package com.gridsandcircles.domain.auth.service;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.gridsandcircles.domain.admin.admin.entity.Admin;
import com.gridsandcircles.domain.admin.admin.repository.AdminRepository;
import com.gridsandcircles.global.ServiceException;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;

  public String loginAdmin(String adminId, String password) {
    Admin admin = getAdmin(adminId);

    if (!passwordEncoder.matches(password, admin.getPassword())) {
      throw new ServiceException(UNAUTHORIZED, "Password does not match");
    }

    return adminId;
  }

  private Admin getAdmin(String adminId) {
    Optional<Admin> admin = adminRepository.findById(adminId);

    if (admin.isEmpty()) {
      throw new NoSuchElementException("Admin not found");
    }

    return admin.get();
  }
}
