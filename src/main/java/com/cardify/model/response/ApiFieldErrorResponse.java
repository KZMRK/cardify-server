package com.cardify.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiFieldErrorResponse {

    private String field;

    private String message;
}
