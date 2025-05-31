package com.cardify.model.type;

import com.openai.core.JsonArray;
import com.openai.core.JsonBoolean;
import com.openai.core.JsonObject;
import com.openai.core.JsonString;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static com.openai.models.ResponseFormatJsonSchema.JsonSchema.Schema;

@Getter
@AllArgsConstructor
public enum OpenAIAssistantType {

    FLASHCARD_GENERATOR("asst_h7o28DcpYGjmM27pHtpkXDdV", "flashcard_generator",
            Schema.builder()
                    .putAdditionalProperty("type", JsonString.of("object"))
                    .putAdditionalProperty("properties", JsonObject.of(
                            Map.of(
                                    "flashcards", JsonObject.of(
                                            Map.of(
                                                    "type", JsonString.of("array"),
                                                    "description", JsonString.of("A list of flashcards, where each card includes a front term and a back definition."),
                                                    "items", JsonObject.of(
                                                            Map.of(
                                                                    "type", JsonString.of("object"),
                                                                    "properties", JsonObject.of(
                                                                            Map.of(
                                                                                    "frontTerm", JsonObject.of(
                                                                                            Map.of(
                                                                                                    "type", JsonString.of("string"),
                                                                                                    "description", JsonString.of("The main term or word displayed on the front of the flashcard.")
                                                                                            )
                                                                                    ),
                                                                                    "backTerm", JsonObject.of(
                                                                                            Map.of(
                                                                                                    "type", JsonString.of("string"),
                                                                                                    "description", JsonString.of("The explanation, translation, or definition shown on the back of the flashcard.")
                                                                                            )
                                                                                    ),
                                                                                    "frontContext", JsonObject.of(
                                                                                            Map.of(
                                                                                                    "type", JsonString.of("string"),
                                                                                                    "description", JsonString.of("An optional sentence or phrase providing context for the front term.")
                                                                                            )
                                                                                    ),
                                                                                    "backContext", JsonObject.of(
                                                                                            Map.of(
                                                                                                    "type", JsonString.of("string"),
                                                                                                    "description", JsonString.of("An optional sentence or phrase providing context or usage for the back definition.")
                                                                                            )
                                                                                    )
                                                                            )
                                                                    ),
                                                                    "required", JsonArray.of(
                                                                            List.of(
                                                                                    JsonString.of("frontTerm"),
                                                                                    JsonString.of("backTerm"),
                                                                                    JsonString.of("frontContext"),
                                                                                    JsonString.of("backContext")
                                                                            )
                                                                    ),
                                                                    "additionalProperties", JsonBoolean.of(false)
                                                            )
                                                    )
                                            )
                                    )
                            )
                    ))
                    .putAdditionalProperty("required", JsonArray.of(List.of(JsonString.of("flashcards"))))
                    .putAdditionalProperty("additionalProperties", JsonBoolean.of(false))
                    .build()
    ),
    KEYWORD_EXTRACTOR("asst_tkev34g9kD9g8RLpHUYUDgsH", "keyword_extractor",
            Schema.builder()
                    .putAdditionalProperty("type", JsonString.from("object"))
                    .putAdditionalProperty("properties", JsonObject.of(
                            Map.of("keywords", JsonObject.of(
                                    Map.of("type", JsonString.of("array"),
                                            "description", JsonString.of("A list of unique words commonly used in everyday conversation, extracted from the provided file or text."),
                                            "items", JsonObject.of(
                                                    Map.of("type", JsonString.of("string"),
                                                            "description", JsonString.of("A single keyword from the source content.")
                                                    )
                                            )
                                    )
                            ))
                    ))
                    .putAdditionalProperty("required", JsonArray.of(List.of(JsonString.of("keywords"))))
                    .putAdditionalProperty("additionalProperties", JsonBoolean.of(false))
                    .build()
    );

    private final String id;
    private final String name;
    private final Schema schema;
}
