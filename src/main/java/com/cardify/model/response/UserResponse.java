package com.cardify.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class UserResponse {

    private String id;

    private String username;

    private String email;

    private CloudFileDto avatar;

    private LocalDate createdAt;
}
