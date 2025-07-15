package com.gridsandcircles.domain.admin.admin;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.gridsandcircles.global.ApiResponse;
import com.gridsandcircles.global.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AdminController", description = "관리자 API")
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/signup")
  @Operation(summary = "회원 가입")
  public ResponseEntity<ApiResponse<AdminResponseDto>> signup(
      @Valid @RequestBody AdminRequestDto adminRequestDto
  ) {
    if (!adminRequestDto.inputPassword().equals(adminRequestDto.confirmPassword())) {
      throw new ServiceException(BAD_REQUEST, "Password does not match");
    }

    AdminResponseDto adminResponseDto = AdminMapper.toDto(
        adminService.createAdmin(adminRequestDto.adminId(), adminRequestDto.inputPassword()));

    return ResponseEntity.status(CREATED)
        .body(new ApiResponse<>("Sign up successful", adminResponseDto));
  }
}
