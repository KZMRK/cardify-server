package com.cardify.model.request;

import com.cardify.model.response.CloudFileDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlashcardDeckRequest {

    private String id;

    private String title;

    private String description;

    private CloudFileDto cover;
}
