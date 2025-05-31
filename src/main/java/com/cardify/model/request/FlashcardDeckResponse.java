package com.cardify.model.request;

import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.response.CloudFileDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class FlashcardDeckResponse {

    private String id;

    private String title;

    private String description;

    private CloudFileDto cover;

    private List<FlashcardDto> flashcards = new ArrayList<>();
}
