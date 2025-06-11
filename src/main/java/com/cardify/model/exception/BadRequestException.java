package com.cardify.model.exception;

import com.cardify.model.type.ApiErrorStatusType;

public class BadRequestException extends CardifyException {

    public BadRequestException(ApiErrorStatusType apiErrorStatusType, String message) {
        super(apiErrorStatusType, message);
    }

    public BadRequestException(String message) {
        super(ApiErrorStatusType.BAD_REQUEST, message);
    }
}
