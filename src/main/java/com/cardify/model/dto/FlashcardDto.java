package com.cardify.model.dto;

import com.cardify.model.response.CloudFileDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class FlashcardDto {

    private String id;

    private String frontTerm;

    private CloudFileDto frontImage;

    private String backTerm;

    private CloudFileDto backImage;

    private String backContext;

    private Boolean isLearned;
}
