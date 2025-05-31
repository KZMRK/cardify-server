package com.cardify.security.authorization;

import com.cardify.model.exception.UnauthorizedException;
import com.cardify.model.response.JwtClaimsResponse;
import com.cardify.model.type.JwtType;
import com.cardify.security.CardifyUserDetails;
import com.cardify.service.JwtService;
import com.cardify.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class CardifyAuthorizationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtClaimsResponse claimsResponse = jwtService.verifyClaims((String) authentication.getCredentials(), JwtType.ACCESS);
        CardifyUserDetails userDetails = (CardifyUserDetails) userService.loadUserByUsername(claimsResponse.getEmail());
        if (ObjectUtils.isEmpty(userDetails) || !StringUtils.equals(userDetails.getId(), claimsResponse.getId())) {
            throw new UnauthorizedException("Invalid JWT token");
        }
        return new CardifyAuthorizationToken(claimsResponse.getId(), Collections.emptySet());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == CardifyAuthorizationToken.class;
    }
}
