package com.cardify.service;

import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.request.BaseFlashcardGenerationRequest;
import com.cardify.model.type.FlashcardGenerationType;

import java.util.List;
import java.util.function.Consumer;

public interface LargeLanguageModelProvider {

    void generateFlashcards(FlashcardGenerationType generationType,
                            BaseFlashcardGenerationRequest request,
                            String transcript,
                            Consumer<List<FlashcardDto>> callback);
}
