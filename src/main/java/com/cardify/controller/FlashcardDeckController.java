package com.cardify.controller;

import com.cardify.model.dto.FlashcardDeckMinResponse;
import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.request.BookFlashcardGenerationRequest;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/flashcard-decks")
@RequiredArgsConstructor
public class FlashcardDeckController {

    private final FlashcardDeckService flashcardDeckService;
    private final FlashcardService flashcardService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public FlashcardDeckMinResponse createFlashcardDeck(@RequestBody FlashcardDeckRequest flashcardDeckRequest) {
        return flashcardDeckService.createFlashcardDeck(flashcardDeckRequest);
    }

    @PostMapping(value = "/{flashcardDeckId}/flashcards", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public FlashcardDto createFlashcard(@PathVariable String flashcardDeckId, @RequestBody FlashcardDto flashcardRequest) {
        return flashcardService.createFlashcard(flashcardDeckId, flashcardRequest);
    }

    @PostMapping(value = "/{flashcardDeckId}/flashcards/generate-ai-youtube", consumes = APPLICATION_JSON_VALUE)
    public void generateFlashcards(@PathVariable String flashcardDeckId,
                                   @RequestBody YouTubeFlashcardGenerationRequest request) {
        flashcardService.generateFlashcards(flashcardDeckId, request);
    }

    @PostMapping(value = "/{flashcardDeckId}/flashcards/generate-ai-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void generateFlashcards(@PathVariable String flashcardDeckId,
                                   @RequestPart BookFlashcardGenerationRequest request,
                                   @RequestPart MultipartFile file) {
        flashcardService.generateFlashcards(flashcardDeckId, request, file);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<FlashcardDeckMinResponse> getAllFlashcardDecks(@RequestParam(required = false) String search,
                                                               @PageableDefault Pageable pageable) {
        return flashcardDeckService.getAllFlashcardDecks(search, pageable);
    }

    @GetMapping(value = "/{flashcardDeckId}", produces = APPLICATION_JSON_VALUE)
    public FlashcardDeckResponse getFlashcardDeckById(@PathVariable String flashcardDeckId) {
        return flashcardDeckService.getFlashcardDeckById(flashcardDeckId);
    }
}
