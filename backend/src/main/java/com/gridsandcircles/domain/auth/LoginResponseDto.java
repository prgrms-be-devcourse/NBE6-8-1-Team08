package com.gridsandcircles.domain.auth;

public record LoginResponseDto(String adminId, String accessToken, String refreshToken) {

}
