package com.cardify.service;

import com.cardify.mapper.FlashcardDeckMapper;
import com.cardify.model.dto.FlashcardDeckMinResponse;
import com.cardify.model.entity.CloudFile;
import com.cardify.model.entity.FlashcardDeck;
import com.cardify.model.request.FlashcardDeckRequest;
import com.cardify.model.request.FlashcardDeckResponse;
import com.cardify.repository.CloudFileRepository;
import com.cardify.repository.FlashcardDeckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlashcardDeckService {

    private final FlashcardDeckMapper flashcardDeckMapper;
    private final CloudFileRepository cloudFileRepository;
    private final FlashcardDeckRepository flashcardDeckRepository;

    @Transactional
    public FlashcardDeckMinResponse createFlashcardDeck(FlashcardDeckRequest flashcardDeckRequest) {
        log.info("[createFlashcardDeck] invoked with flashcardDeckRequest=[{}]", flashcardDeckRequest);
        CloudFile cover = cloudFileRepository.findOneById(flashcardDeckRequest.getCover().getId());
        FlashcardDeck flashcardDeck = flashcardDeckMapper.toEntity(flashcardDeckRequest, cover);
        flashcardDeck = flashcardDeckRepository.save(flashcardDeck);
        return flashcardDeckMapper.toMinDto(flashcardDeck);
    }

    @Transactional(readOnly = true)
    public Page<FlashcardDeckMinResponse> getAllFlashcardDecks(String search, Pageable pageable) {
        log.info("[getAllFlashcardDecks] invoked with search=[{}]", search);
        Page<FlashcardDeck> decks = flashcardDeckRepository.findAllOrderedBySearch(search, pageable);
        return decks.map(flashcardDeckMapper::toMinDto);
    }

    @Transactional(readOnly = true)
    public FlashcardDeckResponse getFlashcardDeckById(String flashcardDeckId) {
        log.info("[getFlashcardDeckById] invoked with flashcardDeckId=[{}]", flashcardDeckId);
        FlashcardDeck flashcardDeck = flashcardDeckRepository.findOneById(flashcardDeckId);
        return flashcardDeckMapper.toDto(flashcardDeck);
    }

    @Transactional
    public FlashcardDeckResponse updateFlashcardDeck(String flashcardDeckId, FlashcardDeckRequest request) {
        return null;
    }

    @Transactional
    public void deleteFlashcardDeckById(String flashcardDeckId) {

    }
}
