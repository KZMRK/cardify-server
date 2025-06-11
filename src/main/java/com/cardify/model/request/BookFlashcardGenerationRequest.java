package com.cardify.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BookFlashcardGenerationRequest extends BaseFlashcardGenerationRequest {

    private Integer startPage;

    private Integer endPage;
}
