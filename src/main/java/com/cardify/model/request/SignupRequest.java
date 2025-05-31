package com.cardify.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "password")
public class SignupRequest {

    private String username;

    private String email;

    private String password;
}
