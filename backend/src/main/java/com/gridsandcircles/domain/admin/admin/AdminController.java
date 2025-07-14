package com.gridsandcircles.domain.admin.admin;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.gridsandcircles.global.ApiResponse;
import com.gridsandcircles.global.ServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<AdminResponseDto>> signup(
      @Valid @RequestBody AdminRequestDto adminRequestDto
  ) {
    if (!adminRequestDto.getInputPassword().equals(adminRequestDto.getConfirmPassword())) {
      throw new ServiceException(BAD_REQUEST, "Password does not match");
    }

    AdminResponseDto adminResponseDto = AdminMapper.toDto(
        adminService.createAdmin(adminRequestDto.getAdminId(), adminRequestDto.getInputPassword()));

    return ResponseEntity.status(CREATED)
        .body(new ApiResponse<>("Sign up successful", adminResponseDto));
  }
}
