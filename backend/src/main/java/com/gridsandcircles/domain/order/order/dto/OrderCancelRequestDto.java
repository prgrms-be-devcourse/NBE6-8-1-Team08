package com.gridsandcircles.domain.order.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "주문 취소 요청 DTO")
public record OrderCancelRequestDto(

        @Schema(
                description = "사용자 이메일",
                example = "order1@example.com"
        )
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @Schema(
                description = "취소할 상품 목록",
                example = "[{\"productName\": \"Columbia Coffee\"}, {\"productName\": \"Espresso\"}]"
        )
        List<ProductRequestDto> products
) {
    @Schema(
            description = "상품명",
            example = "Columbia Coffee"
    )
    public record ProductRequestDto(String productName) {
    }
}
