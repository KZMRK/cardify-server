package com.cardify.model.exception;

import com.cardify.model.type.ApiErrorStatusType;
import lombok.Getter;

@Getter
public class CardifyException extends RuntimeException {

    private ApiErrorStatusType apiErrorStatusType = ApiErrorStatusType.SERVER_ERROR;

    public CardifyException(ApiErrorStatusType apiErrorStatusType, String message) {
        super(message);
        this.apiErrorStatusType = apiErrorStatusType;
    }

    public CardifyException(String message) {
        super(message);
    }
}
