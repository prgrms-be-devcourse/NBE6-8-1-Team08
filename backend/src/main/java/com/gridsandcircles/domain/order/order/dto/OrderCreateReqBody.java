package com.gridsandcircles.domain.order.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderCreateReqBody(
        @NotBlank
        @Size(min = 2, max = 100)
        String address,
        @NotBlank
        @Size(min = 2, max = 100)
        String email,
        int productId,
        int count
){}