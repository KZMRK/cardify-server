package com.cardify.config;

import com.cardify.security.AuthorizationFilter;
import com.cardify.security.CardifyAuthenticationEntryPoint;
import com.cardify.security.authorization.CardifyAuthorizationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Configuration
public class HttpSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   List<AuthorizationFilter> authorizationFilters,
                                                   AuthenticationManager authenticationManager,
                                                   HandlerExceptionResolver handlerExceptionResolver,
                                                   CardifyAuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler((req, res, e) -> handlerExceptionResolver.resolveException(req, res, null, e)))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        .requestMatchers(UrlMappingConfig.getPublicUrls()).permitAll()
                        .anyRequest().authenticated()
                );
        authorizationFilters.forEach(filter -> httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthorizationFilter jwtAuthenticationFilter(CardifyAuthorizationConverter converter) {
        return new AuthorizationFilter(converter);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
