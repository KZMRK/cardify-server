package com.cardify.model.response;

import com.cardify.model.dto.FlashcardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenAIFlashcardResponse {

    private List<FlashcardDto> flashcards;
}
