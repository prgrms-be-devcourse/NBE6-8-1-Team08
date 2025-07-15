package com.gridsandcircles.domain.auth;

import com.gridsandcircles.domain.admin.admin.AdminResponseDto;
import com.gridsandcircles.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "인증 API")
public class AuthController {

  private final AuthService authService;
  private final JwtUtil jwtUtil;

  @PostMapping("login")
  @Operation(summary = "로그인")
  public ResponseEntity<ApiResponse<AdminResponseDto>> login(
      @Valid @RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse response
  ) {
    String adminId = authService.loginAdmin(loginRequestDto.adminId(), loginRequestDto.password());
    String token = jwtUtil.generateToken(adminId);

    ResponseCookie cookie = ResponseCookie.from("access_token", token)
        .httpOnly(true)
        .sameSite("Strict")
        .path("/")
        .maxAge(300)
        .build();

    response.addHeader("Set-Cookie", cookie.toString());

    return ResponseEntity.ok()
        .body(new ApiResponse<>("Login successful", new AdminResponseDto(adminId)));
  }
}
