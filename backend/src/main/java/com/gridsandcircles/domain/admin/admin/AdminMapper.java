package com.gridsandcircles.domain.admin.admin;

public class AdminMapper {

  public static AdminResponseDto toDto(Admin admin) {
    return new AdminResponseDto(admin.getAdminId());
  }
}
