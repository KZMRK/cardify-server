package com.cardify.controller;

import com.cardify.model.request.FlashcardIsLearnedRequest;
import com.cardify.service.FlashcardService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flashcards")
@AllArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @PatchMapping(value = "/{flashcardId}/is-learned", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchFlashcardIsLearned(@PathVariable String flashcardId, @RequestBody FlashcardIsLearnedRequest request) {
        flashcardService.patchFlashcardIsLearned(flashcardId, request.getIsLearned());
    }

    @DeleteMapping("/{flashcardId}")
    public void deleteFlashcardById(@PathVariable String flashcardId) {
        flashcardService.deleteFlashcardById(flashcardId);
    }
}
