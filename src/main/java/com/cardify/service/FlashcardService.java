package com.cardify.service;

import com.cardify.mapper.FlashcardMapper;
import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.entity.CloudFile;
import com.cardify.model.entity.Flashcard;
import com.cardify.model.entity.FlashcardDeck;
import com.cardify.model.exception.NotFoundException;
import com.cardify.model.request.BaseFlashcardGenerationRequest;
import com.cardify.model.request.BookFlashcardGenerationRequest;
import com.cardify.model.request.YouTubeFlashcardGenerationRequest;
import com.cardify.model.response.CloudFileDto;
import com.cardify.model.response.YouTubeTranscriptResponse;
import com.cardify.model.type.FlashcardGenerationType;
import com.cardify.repository.CloudFileRepository;
import com.cardify.repository.FlashcardDeckRepository;
import com.cardify.repository.FlashcardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FlashcardService {

    private final FlashcardDeckRepository flashcardDeckRepository;
    private final FlashcardRepository flashcardRepository;
    private final CloudFileRepository cloudFileRepository;
    private final FlashcardMapper flashcardMapper;
    private final LargeLanguageModelProvider llmProvider;
    private final YouTubeService youTubeService;
    private final OpticalCharacterRecognitionProvider ocrProvider;
    private final PdfDocumentTextExtractorService textExtractorService;

    @Transactional
    public FlashcardDto createFlashcard(String flashcardDeckId, FlashcardDto flashcardRequest) {
        log.info("[createFlashcard] invoked with flashcardDeckId=[{}], flashcardRequest=[{}]", flashcardDeckId, flashcardRequest);
        FlashcardDeck flashcardDeck = flashcardDeckRepository.findOneById(flashcardDeckId);
        CloudFile frontImage = getCloudFile(flashcardRequest.getFrontImage());
        CloudFile backImage = getCloudFile(flashcardRequest.getBackImage());
        Flashcard flashcard = flashcardMapper.toEntity(flashcardRequest, frontImage, backImage);
        flashcard = flashcardRepository.save(flashcard);
        flashcardDeck.addFlashcard(flashcard);
        return flashcardMapper.toDto(flashcard);
    }

    public void generateFlashcards(String flashcardDeckId, YouTubeFlashcardGenerationRequest request) {
        log.info("[generateFlashcards] invoked with flashcardDeckId=[{}], request=[{}]", flashcardDeckId, request);
        String transcript = getVideoTranscript(request.getVideoId(), request.getStartTime(), request.getEndTime());
        llmProvider.generateFlashcards(FlashcardGenerationType.YOUTUBE, request, transcript, dtos -> saveGeneratedFlashcards(flashcardDeckId, dtos));
    }


    public void generateFlashcards(String flashcardDeckId, BookFlashcardGenerationRequest request, MultipartFile file) {
        log.info("[generateFlashcards] invoked with flashcardDeckId=[{}], request=[{}], fileName=[{}]", flashcardDeckId, request, file.getOriginalFilename());
        String bookText = textExtractorService.extract(file, request.getStartPage(), request.getEndPage());
        llmProvider.generateFlashcards(FlashcardGenerationType.BOOK, request, bookText, dtos -> saveGeneratedFlashcards(flashcardDeckId, dtos));
    }

    public void generateFlashcards(String flashcardDeckId, BaseFlashcardGenerationRequest request, List<MultipartFile> files) {
        log.info("[generateFlashcards] invoked with flashcardDeckId=[{}], request=[{}]", flashcardDeckId, request);
        String imagesText = files.stream()
                .map(ocrProvider::extractText)
                .collect(Collectors.joining("\n"));
        llmProvider.generateFlashcards(FlashcardGenerationType.IMAGE, request, imagesText, dtos -> saveGeneratedFlashcards(flashcardDeckId, dtos));
    }

    @Transactional
    public void patchFlashcardIsLearned(String flashcardId, Boolean isLearned) {
        log.info("[patchFlashcardIsLearned] invoked with flashcardId=[{}], isLearned=[{}]", flashcardId, isLearned);
        Flashcard flashcard = flashcardRepository.findOneById(flashcardId);
        flashcard.setIsLearned(isLearned);
    }

    @Transactional
    public FlashcardDto updateFlashcardById(String flashcardId, FlashcardDto dto) {
        return null;
    }


    @Transactional
    public void deleteFlashcardById(String flashcardId) {
        log.info("[deleteFlashcardById] invoked with flashcardId=[{}]", flashcardId);
    }

    private CloudFile getCloudFile(CloudFileDto cloudFileDto) {
        return Optional.ofNullable(cloudFileDto)
                .filter(c -> StringUtils.isNotBlank(c.getId()))
                .map(c -> cloudFileRepository.findOneById(c.getId()))
                .orElse(null);
    }

    private String getVideoTranscript(String videoId, Double startTime, Double endTime) {
        YouTubeTranscriptResponse videoSubtitles = youTubeService.getVideoSubtitles(videoId);
        return videoSubtitles.getTexts().stream()
                .filter(t -> t.getStart() >= startTime && t.getStart() <= endTime)
                .map(YouTubeTranscriptResponse.Text::getText)
                .collect(Collectors.joining());
    }

    private void saveGeneratedFlashcards(String flashcardDeckId, List<FlashcardDto> dtos) {
        log.info("[saveGeneratedFlashcards] invoked with flashcardDeckId=[{}], dtos=[{}]", flashcardDeckId, dtos);
        FlashcardDeck flashcardDeck = flashcardDeckRepository.findById(flashcardDeckId)
                .orElseThrow(() -> new NotFoundException("resource not found"));
        dtos.stream()
                    .map(f -> flashcardMapper.toEntity(f, null, null))
                    .forEach(flashcardDeck::addFlashcard);
        flashcardDeckRepository.save(flashcardDeck);
    }
}

