package com.cardify.controller;

import com.cardify.service.FlashcardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flashcards")
@AllArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @DeleteMapping("/{flashcardId}")
    public void deleteFlashcardById(@PathVariable String flashcardId) {
        flashcardService.deleteFlashcardById(flashcardId);
    }
}
