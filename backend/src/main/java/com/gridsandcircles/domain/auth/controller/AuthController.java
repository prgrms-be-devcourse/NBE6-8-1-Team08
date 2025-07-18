package com.gridsandcircles.domain.auth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.auth.dto.LoginRequestDto;
import com.gridsandcircles.domain.auth.dto.LoginResponseDto;
import com.gridsandcircles.domain.auth.service.AuthService;
import com.gridsandcircles.domain.auth.util.JwtUtil;
import com.gridsandcircles.global.ResultResponse;
import com.gridsandcircles.global.swagger.NotFoundApiResponse;
import com.gridsandcircles.global.swagger.UnauthorizedApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "인증 API")
public class AuthController {

  private final AuthService authService;
  private final JwtUtil jwtUtil;

  @PostMapping("login")
  @Operation(summary = "로그인")
  @ApiResponse(
      responseCode = "200",
      description = "로그인 성공",
      content = @Content(
          mediaType = APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ResultResponse.class),
          examples = @ExampleObject(value = """
              {
                "msg": "Login successful",
                    "data": {
                        "adminId": "test",
                        "accessToken": "eyJhbGciOiJIUzI1N...",
                        "refreshToken": "fed97514-7676-4..."
                    }
              }
              """
          )
      )
  )
  @UnauthorizedApiResponse
  @NotFoundApiResponse
  public ResponseEntity<ResultResponse<LoginResponseDto>> login(
      @Valid @RequestBody LoginRequestDto loginRequestDto
  ) {
    String adminId = authService.loginAdmin(loginRequestDto.adminId(), loginRequestDto.password());
    String accessToken = jwtUtil.generateToken(adminId);
    String refreshToken = authService.createRefreshToken(adminId);

    return ResponseEntity.ok().body(new ResultResponse<>("Login successful",
        new LoginResponseDto(adminId, accessToken, refreshToken)));
  }

  @DeleteMapping("/logout")
  @Operation(summary = "로그아웃")
  @ApiResponse(responseCode = "204", description = "로그아웃 성공")
  public ResponseEntity<Void> logout(Principal principal) {
    authService.deleteRefreshToken(principal.getName());
    return ResponseEntity.noContent().build();
  }
}
