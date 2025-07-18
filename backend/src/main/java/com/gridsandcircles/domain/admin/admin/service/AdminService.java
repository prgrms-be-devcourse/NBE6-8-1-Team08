package com.gridsandcircles.domain.admin.admin.service;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.gridsandcircles.domain.admin.admin.entity.Admin;
import com.gridsandcircles.domain.admin.admin.repository.AdminRepository;
import com.gridsandcircles.global.ServiceException;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {

  private final PasswordEncoder passwordEncoder;
  private final AdminRepository adminRepository;

  @Transactional
  public Admin createAdmin(String adminId, String password) {
    if (adminRepository.existsById(adminId)) {
      throw new ServiceException(CONFLICT, "Admin id " + adminId + " already exists");
    }

    return adminRepository.save(new Admin(adminId, passwordEncoder.encode(password)));
  }

  public Admin getAdmin(String adminId) {
    Optional<Admin> admin = adminRepository.findById(adminId);

    if (admin.isEmpty()) {
      throw new NoSuchElementException("Admin not found");
    }

    return admin.get();
  }
}