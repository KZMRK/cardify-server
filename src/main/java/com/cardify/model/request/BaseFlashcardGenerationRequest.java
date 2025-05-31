package com.cardify.model.request;

import com.cardify.model.type.LanguageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseFlashcardGenerationRequest {

    private Integer flashcardsCount;

    private LanguageType targetLanguageType;
}
