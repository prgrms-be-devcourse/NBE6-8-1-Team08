package com.gridsandcircles.domain.auth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.auth.dto.LoginRequestDto;
import com.gridsandcircles.domain.auth.dto.LoginResponseDto;
import com.gridsandcircles.domain.auth.dto.RefreshTokenRequestDto;
import com.gridsandcircles.domain.auth.dto.RefreshTokenResponseDto;
import com.gridsandcircles.domain.auth.service.AuthService;
import com.gridsandcircles.global.ResultResponse;
import com.gridsandcircles.global.swagger.BadRequestApiResponse;
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

  @PostMapping("/login")
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
    return ResponseEntity.ok().body(new ResultResponse<>("Login successful",
        authService.loginAdmin(loginRequestDto.adminId(), loginRequestDto.password())));
  }

  @DeleteMapping("/logout")
  @Operation(summary = "로그아웃")
  @ApiResponse(responseCode = "204", description = "로그아웃 성공")
  public ResponseEntity<Void> logout(Principal principal) {
    authService.deleteRefreshToken(principal.getName());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/refresh")
  @Operation(summary = "Access token 재발급 및 새 Refresh token으로 교체")
  @ApiResponse(
      responseCode = "200",
      description = "토큰 재발급 성공",
      content = @Content(
          mediaType = APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ResultResponse.class),
          examples = @ExampleObject(value = """
              {
                "msg": "Refreshing tokens successful",
                    "data": {
                        "accessToken": "eyJhbGciOiJIUzI1N...",
                        "refreshToken": "fed97514-7676-4..."
                    }
              }
              """
          )
      )
  )
  @BadRequestApiResponse
  @UnauthorizedApiResponse
  @NotFoundApiResponse
  public ResponseEntity<ResultResponse<RefreshTokenResponseDto>> refresh(
      @RequestBody RefreshTokenRequestDto refreshTokenRequestDto
  ) {
    RefreshTokenResponseDto refreshTokenResponseDto = authService.refreshTokens(
        refreshTokenRequestDto.expiredAccessToken(), refreshTokenRequestDto.originalRefreshToken());

    return ResponseEntity.ok()
        .body(new ResultResponse<>("Refreshing tokens successful", refreshTokenResponseDto));
  }
}
