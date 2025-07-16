package com.gridsandcircles.domain.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.admin.admin.AdminResponseDto;
import com.gridsandcircles.global.ResultResponse;
import com.gridsandcircles.global.swagger.NotFoundApiResponse;
import com.gridsandcircles.global.swagger.UnauthorizedApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
                  "adminId": "test"
                }
              }
              """
          )
      )
  )
  @UnauthorizedApiResponse
  @NotFoundApiResponse
  public ResponseEntity<ResultResponse<AdminResponseDto>> login(
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
        .body(new ResultResponse<>("Login successful", new AdminResponseDto(adminId)));
  }
}
