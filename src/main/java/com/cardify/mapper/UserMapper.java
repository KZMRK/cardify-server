package com.cardify.mapper;

import com.cardify.model.entity.User;
import com.cardify.model.request.SignupRequest;
import com.cardify.model.response.UserResponse;
import com.cardify.security.CardifyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final CloudFileMapper cloudFileMapper;

    public User toEntity(SignupRequest signupRequest, String encodedPassword) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encodedPassword);
        return user;
    }

    public CardifyUserDetails toDetails(User user) {
        return CardifyUserDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserResponse toResponse(User entity) {
        return new UserResponse()
                .setId(entity.getId())
                .setUsername(entity.getUsername())
                .setEmail(entity.getEmail())
                .setAvatar(cloudFileMapper.toDto(entity.getAvatar()))
                .setCreatedAt(entity.getCreatedAt().toLocalDate());
    }
}
