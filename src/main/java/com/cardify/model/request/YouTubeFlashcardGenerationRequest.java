package com.cardify.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class YouTubeFlashcardGenerationRequest extends BaseFlashcardGenerationRequest {

    private String videoId;

    private Double startTime;

    private Double endTime;
}
