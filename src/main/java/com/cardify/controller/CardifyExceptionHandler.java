package com.cardify.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cardify.model.exception.BadRequestException;
import com.cardify.model.exception.CardifyException;
import com.cardify.model.exception.NotFoundException;
import com.cardify.model.exception.UnauthorizedException;
import com.cardify.model.response.ApiErrorResponse;
import com.cardify.model.response.ApiFieldErrorResponse;
import com.cardify.model.type.ApiErrorStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.Collections.emptyList;

@Slf4j
@RestControllerAdvice
public class CardifyExceptionHandler {

    private static final String VALIDATION_ERROR_MESSAGE = "validation failed";

    @ExceptionHandler(CardifyException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CardifyException e) {
        return ResponseEntity.badRequest().body(build(ApiErrorStatusType.SERVER_ERROR, e));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BadRequestException e) {
        return ResponseEntity.badRequest().body(build(ApiErrorStatusType.BAD_REQUEST, e));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(NotFoundException e) {
        return new ResponseEntity<>(build(ApiErrorStatusType.NOT_FOUND, e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UnauthorizedException e) {
        return new ResponseEntity<>(build(ApiErrorStatusType.UNAUTHORIZED, e), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(JWTDecodeException e) {
        return new ResponseEntity<>(build(ApiErrorStatusType.UNAUTHORIZED, e), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(build(ApiErrorStatusType.NOT_FOUND, e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ApiFieldErrorResponse> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiFieldErrorResponse().setField(error.getField()).setMessage(error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(build(ApiErrorStatusType.VALIDATION_ERROR, VALIDATION_ERROR_MESSAGE, errors));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(RuntimeException e) {
        log.error("Exception [{}]", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(build(ApiErrorStatusType.SERVER_ERROR, e));
    }

    private ApiErrorResponse build(ApiErrorStatusType status, Exception e) {
        return build(status, e, emptyList());
    }

    private ApiErrorResponse build(ApiErrorStatusType status, Exception e, List<ApiFieldErrorResponse> errors) {
        return build(status, e.getMessage(), errors);
    }

    private ApiErrorResponse build(ApiErrorStatusType status, String message, List<ApiFieldErrorResponse> errors) {
        return new ApiErrorResponse()
                .setErrorStatusType(status)
                .setMessage(message)
                .setFields(errors);
    }
}
