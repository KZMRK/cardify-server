package com.cardify.controller;

import com.cardify.model.dto.FlashcardDeckMinResponse;
import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.request.FlashcardDeckRequest;
import com.cardify.model.request.FlashcardDeckResponse;
import com.cardify.model.request.YouTubeFlashcardGenerationRequest;
import com.cardify.service.FlashcardDeckService;
import com.cardify.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flashcard-decks")
@RequiredArgsConstructor
public class FlashcardDeckController {

    private final FlashcardDeckService flashcardDeckService;
    private final FlashcardService flashcardService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FlashcardDeckMinResponse createFlashcardDeck(@RequestBody FlashcardDeckRequest flashcardDeckRequest) {
        return flashcardDeckService.createFlashcardDeck(flashcardDeckRequest);
    }

    @PostMapping(value = "/{flashcardDeckId}/flashcards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FlashcardDto createFlashcard(@PathVariable String flashcardDeckId, @RequestBody FlashcardDto flashcardRequest) {
        return flashcardService.createFlashcard(flashcardDeckId, flashcardRequest);
    }

    @PostMapping(value = "/{flashcardDeckId}/flashcards/generate-ai-youtube")
    public void generateFlashcards(@PathVariable String flashcardDeckId,
                                   @RequestBody YouTubeFlashcardGenerationRequest request) {
        flashcardService.generateFlashcards(flashcardDeckId, request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FlashcardDeckMinResponse> getAllFlashcardDecks(@RequestParam(required = false) String search,
                                                               @PageableDefault Pageable pageable) {
        return flashcardDeckService.getAllFlashcardDecks(search, pageable);
    }

    @GetMapping(value = "/{flashcardDeckId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlashcardDeckResponse getFlashcardDeckById(@PathVariable String flashcardDeckId) {
        return flashcardDeckService.getFlashcardDeckById(flashcardDeckId);
    }
}
