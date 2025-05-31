package com.cardify.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cardify.model.exception.UnauthorizedException;
import com.cardify.model.property.JwtProperties;
import com.cardify.model.response.AuthenticationResponse;
import com.cardify.model.response.AuthenticationResponse.JwtTokenResponse;
import com.cardify.model.response.JwtClaimsResponse;
import com.cardify.model.type.JwtType;
import com.cardify.security.CardifyUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class JwtService {

    private static final String BEARER_HEADER_NAME = "Bearer ";
    private static final String JWT_TYPE_KEY = "jwtType";
    private static final String USER_ID_KEY = "userId";
    private static final String USER_EMAIL_KEY = "userEmail";

    private final JwtProperties jwtProperties;

    public AuthenticationResponse createAuthentication(UserDetails userDetails) {
        JwtTokenResponse accessToken = createToken(userDetails, JwtType.ACCESS);
        JwtTokenResponse refreshToken = createToken(userDetails, JwtType.REFRESH);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtClaimsResponse verifyClaims(String authorizationHeader, JwtType expectedJwtType) {
        String token = getBearerToken(authorizationHeader);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(jwtProperties.signingKey())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String jwtIssuer = decodedJWT.getIssuer();
        String jwtType = decodedJWT.getClaim(JWT_TYPE_KEY).asString();

        if (!StringUtils.equals(jwtIssuer, jwtProperties.issuer())) {
            log.error("[verifyClaims] Invalid JWT issuer, expected: {}, got: {}", jwtProperties.issuer(), jwtIssuer);
            throw new UnauthorizedException("Invalid JWT token");
        }
        if (ObjectUtils.notEqual(jwtType, expectedJwtType.name())) {
            log.error("[verifyClaims] Invalid JWT type, expected: {}, got: {}", expectedJwtType, jwtType);
            throw new UnauthorizedException("Invalid JWT token");
        }
        return new JwtClaimsResponse()
                .setId(decodedJWT.getClaim(USER_ID_KEY).asString())
                .setEmail(decodedJWT.getClaim(USER_EMAIL_KEY).asString());
    }

    private JwtTokenResponse createToken(UserDetails userDetails, JwtType jwtType) {
        CardifyUserDetails details = (CardifyUserDetails) userDetails;
        Instant issuedAt = Instant.now();
        Long expirationTimeInterval = switch (jwtType) {
            case ACCESS -> jwtProperties.accessJwtExpTime();
            case REFRESH -> jwtProperties.refreshJwtExpTime();
        };
        Instant expiration = issuedAt.plus(expirationTimeInterval, ChronoUnit.SECONDS);

        String token = JWT.create()
                .withClaim(JWT_TYPE_KEY, jwtType.name())
                .withClaim(USER_ID_KEY, details.getId())
                .withClaim(USER_EMAIL_KEY, details.getUsername())
                .withIssuer(jwtProperties.issuer())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC512(jwtProperties.signingKey()));
        return JwtTokenResponse.builder()
                .jwtToken(token)
                .expiresIn(expirationTimeInterval)
                .build();
    }

    private String getBearerToken(String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(header -> header.startsWith(BEARER_HEADER_NAME))
                .map(header -> header.substring(BEARER_HEADER_NAME.length()))
                .orElseThrow(() -> new UnauthorizedException("invalid access token"));
    }
}
