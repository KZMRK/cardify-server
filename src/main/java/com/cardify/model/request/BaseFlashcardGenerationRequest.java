package com.cardify.model.request;

import com.cardify.model.type.LanguageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseFlashcardGenerationRequest {

    private Integer flashcardsCount;

    private LanguageType sourceLanguageType;
}
