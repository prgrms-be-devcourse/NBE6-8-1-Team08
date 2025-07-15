package com.gridsandcircles.domain.auth;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  @Size(min = 4, max = 10)
  private String adminId;

  @Size(min = 10, max = 20)
  private String password;
}
