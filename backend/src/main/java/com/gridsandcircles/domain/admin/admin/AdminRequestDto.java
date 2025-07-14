package com.gridsandcircles.domain.admin.admin;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequestDto {

  @Size(min = 4, max = 10)
  private String adminId;

  @Size(min = 10, max = 20)
  private String inputPassword;

  @Size(min = 10, max = 20)
  private String confirmPassword;
}
