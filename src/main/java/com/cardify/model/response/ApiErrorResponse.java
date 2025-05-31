package com.cardify.model.response;

import com.cardify.model.type.ApiErrorStatusType;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ApiErrorResponse {

    private ApiErrorStatusType errorStatusType;

    private String message;

    private List<ApiFieldErrorResponse> fields = new ArrayList<>();
}
