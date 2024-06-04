package com.nashtech.rookies.ecommerce.dto.user.responses;

public record AuthResponseTokenDTO(String username, String accessToken, String refreshToken) {
}
