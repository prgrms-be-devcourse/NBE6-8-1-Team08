package com.gridsandcircles.domain.auth.dto;

import jakarta.validation.constraints.Size;

public record LoginRequestDto(
    @Size(min = 4, max = 10) String adminId,
    @Size(min = 10, max = 20) String password
) {

}
