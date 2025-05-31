package com.cardify.service;

import com.cardify.mapper.UserMapper;
import com.cardify.model.entity.User;
import com.cardify.model.exception.NotFoundException;
import com.cardify.model.request.SignupRequest;
import com.cardify.model.response.UserResponse;
import com.cardify.repository.UserRepository;
import com.cardify.security.CardifyContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserDetails createUser(SignupRequest signupRequest) {
        log.info("[createUser] invoked with signupRequest=[{}]", signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = userMapper.toEntity(signupRequest, encodedPassword);
        user = userRepository.save(user);
        return userMapper.toDetails(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        log.info("[getCurrentUser] invoked");
        User user = userRepository.findById(CardifyContextHolder.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(userMapper::toDetails)
                .orElse(null);
    }
}
