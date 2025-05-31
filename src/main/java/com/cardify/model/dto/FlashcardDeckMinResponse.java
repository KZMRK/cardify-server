package com.cardify.model.dto;

import com.cardify.model.response.CloudFileDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FlashcardDeckMinResponse {

    private String id;

    private String title;

    private CloudFileDto cover;

    private Integer flashcardsCount;

    private Integer learnedFlashcardsCount;
}
