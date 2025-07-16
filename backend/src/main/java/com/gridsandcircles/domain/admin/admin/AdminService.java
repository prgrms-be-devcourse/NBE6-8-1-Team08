package com.gridsandcircles.domain.admin.admin;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.gridsandcircles.global.ServiceException;
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
}