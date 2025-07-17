package com.gridsandcircles.domain.admin.admin.mapper;

import com.gridsandcircles.domain.admin.admin.dto.AdminSignupResponseDto;
import com.gridsandcircles.domain.admin.admin.entity.Admin;

public class AdminMapper {

  public static AdminSignupResponseDto toDto(Admin admin) {
    return new AdminSignupResponseDto(admin.getAdminId());
  }
}
