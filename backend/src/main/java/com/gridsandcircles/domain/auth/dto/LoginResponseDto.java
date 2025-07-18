package com.gridsandcircles.domain.auth.dto;

public record LoginResponseDto(String adminId, String accessToken, String refreshToken) {

}
