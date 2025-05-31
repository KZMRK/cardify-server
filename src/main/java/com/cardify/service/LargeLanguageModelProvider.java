package com.cardify.service;

import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.request.BaseFlashcardGenerationRequest;
import com.cardify.model.type.FlashcardGenerationType;

import java.util.List;

public interface LargeLanguageModelProvider {

    List<FlashcardDto> generateFlashcards(FlashcardGenerationType generationType, BaseFlashcardGenerationRequest request, String transcript);
}
