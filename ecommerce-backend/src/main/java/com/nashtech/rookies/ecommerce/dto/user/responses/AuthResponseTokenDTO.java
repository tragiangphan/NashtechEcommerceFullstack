package com.nashtech.rookies.ecommerce.dto.user.responses;

public record AuthResponseTokenDTO(String username, Long roleId, String accessToken, String refreshToken) {
}
