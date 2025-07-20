package com.gridsandcircles.domain.admin.admin.dto;

import jakarta.validation.constraints.Size;

public record AdminSignupRequestDto(
    @Size(min = 4, max = 10, message = "Id는 4~10자여야 합니다.") String adminId,
    @Size(min = 10, max = 20, message = "비밀번호는 10~20자여야 합니다.") String inputPassword,
    @Size(min = 10, max = 20, message = "비밀번호는 10~20자여야 합니다.") String confirmPassword
) {

}