package com.cardify.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.cardify.config.UrlMappingConfig.getPublicUrls;
import static java.util.function.Predicate.not;
import static org.apache.commons.lang3.ArrayUtils.contains;

@Component
public class CardifyAuthorizationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(not(header -> contains(getPublicUrls(), request.getServletPath())))
                .map(CardifyAuthorizationToken::new)
                .orElse(null);
    }
}
