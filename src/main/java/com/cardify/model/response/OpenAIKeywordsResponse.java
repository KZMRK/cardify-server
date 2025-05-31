package com.cardify.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenAIKeywordsResponse {

    private List<String> keywords;
}
