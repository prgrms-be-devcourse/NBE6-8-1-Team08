package com.gridsandcircles.domain.admin.admin.mapper;

import com.gridsandcircles.domain.admin.admin.dto.AdminResponseDto;
import com.gridsandcircles.domain.admin.admin.entity.Admin;

public class AdminMapper {

  public static AdminResponseDto toDto(Admin admin) {
    return new AdminResponseDto(admin.getAdminId());
  }
}
