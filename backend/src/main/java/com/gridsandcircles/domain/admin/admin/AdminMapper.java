package com.gridsandcircles.domain.admin.admin;

public class AdminMapper {

  public static AdminSignupResponseDto toDto(Admin admin) {
    return new AdminSignupResponseDto(admin.getAdminId());
  }
}
