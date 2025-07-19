package com.gridsandcircles.domain.order.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto(
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @NotBlank(message = "주소는 필수입니다.")
        String address,

        @NotEmpty(message = "주문 아이템은 최소 1개 이상이어야 합니다.")
        List<OrderItemRequestDto> orderItems
) {
    public record OrderItemRequestDto(
            @NotNull(message = "상품 ID는 필수입니다.")
            Long productId,

            @Min(value = 1, message = "주문 수량은 1개 이상이어야 합니다.")
            int count
    ) {}
}