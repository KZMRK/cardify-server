package com.cardify.security.authorization;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class CardifyAuthorizationToken extends PreAuthenticatedAuthenticationToken {

    public CardifyAuthorizationToken(String accessToken) {
        super(null, accessToken);
    }

    public CardifyAuthorizationToken(String userId, Collection<? extends GrantedAuthority> authorities) {
        super(userId, null, authorities);
    }
}
