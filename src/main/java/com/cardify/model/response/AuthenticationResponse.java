package com.cardify.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private JwtTokenResponse accessToken;

    private JwtTokenResponse refreshToken;

    @Getter
    @Setter
    @Builder
    public static class JwtTokenResponse {

        private String jwtToken;

        private Long expiresIn;
    }
}
