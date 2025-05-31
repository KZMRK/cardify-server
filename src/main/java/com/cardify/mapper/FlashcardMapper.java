package com.cardify.mapper;

import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.entity.CloudFile;
import com.cardify.model.entity.Flashcard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FlashcardMapper {

    private final CloudFileMapper cloudFileMapper;

    public Flashcard toEntity(FlashcardDto dto, CloudFile frontImage, CloudFile backImage) {
        Flashcard flashcard = new Flashcard();
        flashcard.setFrontTerm(dto.getFrontTerm());
        flashcard.setFrontImage(frontImage);
        flashcard.setBackTerm(dto.getBackTerm());
        flashcard.setBackImage(backImage);
        flashcard.setBackContext(dto.getBackContext());
        return flashcard;
    }

    public FlashcardDto toDto(Flashcard entity) {
        return new FlashcardDto()
                .setId(entity.getId())
                .setFrontTerm(entity.getFrontTerm())
                .setFrontImage(cloudFileMapper.toDto(entity.getFrontImage()))
                .setBackTerm(entity.getBackTerm())
                .setBackImage(cloudFileMapper.toDto(entity.getBackImage()))
                .setBackContext(entity.getBackContext())
                .setIsLearned(entity.getIsLearned());
    }
}
