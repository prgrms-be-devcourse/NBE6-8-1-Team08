package com.gridsandcircles.domain.admin.admin;

import jakarta.validation.constraints.Size;

public record AdminSignupRequestDto(
    @Size(min = 4, max = 10) String adminId,
    @Size(min = 10, max = 20) String inputPassword,
    @Size(min = 10, max = 20) String confirmPassword
) {

}
