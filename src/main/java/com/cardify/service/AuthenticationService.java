package com.cardify.service;

import com.cardify.model.exception.BadRequestException;
import com.cardify.model.request.LoginRequest;
import com.cardify.model.request.SignupRequest;
import com.cardify.model.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse signup(SignupRequest signupRequest) {
        log.info("[signup] invoked with signupRequest=[{}]", signupRequest);
        UserDetails userDetails = userService.loadUserByUsername(signupRequest.getEmail());
        if (ObjectUtils.isNotEmpty(userDetails)) {
            log.error("[authenticate] User [{}] already exists", signupRequest.getEmail());
            throw new BadRequestException("User with email [%s] already exists".formatted(signupRequest.getEmail()));
        }
        userDetails = userService.createUser(signupRequest);
        return jwtService.createAuthentication(userDetails);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        log.info("[login] invoked with loginRequest=[{}]", loginRequest);
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        if (ObjectUtils.isEmpty(userDetails) || !passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadRequestException("Неправильна електронна пошта або пароль");
        }
        return jwtService.createAuthentication(userDetails);
    }
}
