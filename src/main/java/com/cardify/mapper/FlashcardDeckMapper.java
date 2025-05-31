package com.cardify.mapper;

import com.cardify.model.dto.FlashcardDeckMinResponse;
import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.entity.CloudFile;
import com.cardify.model.entity.Flashcard;
import com.cardify.model.entity.FlashcardDeck;
import com.cardify.model.request.FlashcardDeckRequest;
import com.cardify.model.request.FlashcardDeckResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FlashcardDeckMapper {

    private final CloudFileMapper cloudFileMapper;
    private final FlashcardMapper flashcardMapper;

    public FlashcardDeck toEntity(FlashcardDeckRequest request, CloudFile cover) {
        FlashcardDeck entity = new FlashcardDeck();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCover(cover);
        return entity;
    }

    public FlashcardDeckMinResponse toMinDto(FlashcardDeck entity) {
        int learnedFlashcardsCount = (int) entity.getFlashcards().stream()
                .filter(Flashcard::getIsLearned)
                .count();
        return new FlashcardDeckMinResponse()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setCover(cloudFileMapper.toDto(entity.getCover()))
                .setFlashcardsCount(entity.getFlashcards().size())
                .setLearnedFlashcardsCount(learnedFlashcardsCount);
    }

    public FlashcardDeckResponse toDto(FlashcardDeck entity) {
        List<FlashcardDto> flashcards = entity.getFlashcards().stream()
                .map(flashcardMapper::toDto)
                .toList();
        return new FlashcardDeckResponse()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setDescription(entity.getDescription())
                .setCover(cloudFileMapper.toDto(entity.getCover()))
                .setFlashcards(flashcards);
    }
}
