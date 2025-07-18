package com.gridsandcircles.domain.auth.dto;

public record RefreshTokenRequestDto(String expiredAccessToken, String originalRefreshToken) {

}
