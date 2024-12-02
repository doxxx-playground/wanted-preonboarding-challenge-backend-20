package com.wanted.market.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    
    private String token;
    private String tokenType = "Bearer";

    @Builder
    public TokenResponse(String token) {
        this.token = token;
    }
}