package com.cardify.service;

import com.cardify.model.dto.FlashcardDto;
import com.cardify.model.exception.CardifyException;
import com.cardify.model.request.BaseFlashcardGenerationRequest;
import com.cardify.model.response.OpenAIFlashcardResponse;
import com.cardify.model.response.OpenAIKeywordsResponse;
import com.cardify.model.type.FlashcardGenerationType;
import com.cardify.model.type.OpenAIAssistantType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.core.MultipartField;
import com.openai.models.ResponseFormatJsonSchema;
import com.openai.models.beta.threads.ThreadCreateAndRunParams;
import com.openai.models.beta.threads.ThreadCreateAndRunParams.Thread.Message.Attachment;
import com.openai.models.beta.threads.messages.Message;
import com.openai.models.beta.threads.messages.MessageListPage;
import com.openai.models.beta.threads.messages.MessageListParams;
import com.openai.models.beta.threads.runs.Run;
import com.openai.models.beta.threads.runs.RunRetrieveParams;
import com.openai.models.beta.threads.runs.RunStatus;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService implements LargeLanguageModelProvider {

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FlashcardDto> generateFlashcards(FlashcardGenerationType generationType,
                                                 BaseFlashcardGenerationRequest request,
                                                 String text) {
        log.info("generateFlashcards] invoked with generationType=[{}], request=[{}], text=[{}]", generationType, request, text);
        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        String fileId = uploadFile(inputStream).id();
        var attachment = buildAttachment(fileId);
        List<String> keywords = extractKeywords(generationType, request, attachment);
        log.info("[generateFlashcards] keywords=[{}]", keywords);
        String prompt = generationType.formatCardsPrompt(request.getTargetLanguageType().getName(), keywords);
        var message = buildMessage(prompt, attachment);
        Run run = createThreadAndRun(OpenAIAssistantType.FLASHCARD_GENERATOR, message);
        waitRunEnd(run);
        var messages = getThreadMessages(run.threadId());
        String responseMessage = getAssistantAnswer(messages);
        return objectMapper.readValue(responseMessage, OpenAIFlashcardResponse.class).getFlashcards();
    }

    @SneakyThrows
    private List<String> extractKeywords(FlashcardGenerationType generationType, BaseFlashcardGenerationRequest request, Attachment attachment) {
        String prompt = generationType.formatWordsPrompt(request.getFlashcardsCount());
        var run = createThreadAndRun(OpenAIAssistantType.KEYWORD_EXTRACTOR, prompt, attachment);
        waitRunEnd(run);
        var messages = getThreadMessages(run.threadId());
        String answer = getAssistantAnswer(messages);
        return objectMapper.readValue(answer, OpenAIKeywordsResponse.class).getKeywords();
    }

    private void waitRunEnd(Run run) {
        log.info("[waitRunEnd] runId=[{}]", run.id());
        log.info("[waitRunEnd] threadId=[{}]", run.threadId());
        com.openai.models.beta.threads.runs.RunStatus runStatus = run.status();
        while (runStatus.equals(RunStatus.QUEUED) || runStatus.equals(RunStatus.IN_PROGRESS)) {
            runStatus = getRunStatusById(run.threadId(), run.id());
        }
        if (!runStatus.equals(RunStatus.COMPLETED)) {
            log.warn("[generateFlashcards] runStatus=[{}]", runStatus);
            throw new CardifyException("Generation Failed");
        }
    }

    private String getAssistantAnswer(MessageListPage messages) {
        return messages.data().stream()
                .filter(m -> m.role().equals(Message.Role.ASSISTANT))
                .findAny()
                .orElseThrow(() -> new CardifyException("Generation Failed"))
                .content()
                .getFirst()
                .asText()
                .text()
                .value();
    }

    private MessageListPage getThreadMessages(String threadId) {
        var messageListParams = MessageListParams.builder()
                .threadId(threadId)
                .build();
        return openAIClient.beta()
                .threads()
                .messages()
                .list(messageListParams);
    }

    private RunStatus getRunStatusById(String threadId, String runId) {
        RunRetrieveParams threadRunRetrieveParams = RunRetrieveParams.builder()
                .threadId(threadId)
                .runId(runId)
                .build();
        return openAIClient.beta()
                .threads()
                .runs()
                .retrieve(threadRunRetrieveParams)
                .status();
    }

    private FileObject uploadFile(InputStream file) {
        MultipartField<InputStream> fileField = MultipartField.<InputStream>builder()
                .value(file)
                .contentType("text/plain")
                .filename("transcript.txt")
                .build();
        var createParams = com.openai.models.files.FileCreateParams.builder()
                .file(fileField)
                .purpose(FilePurpose.ASSISTANTS)
                .build();
        return openAIClient.files()
                .create(createParams);
    }

    private Run createThreadAndRun(OpenAIAssistantType assistantType, String prompt, Attachment attachment) {
        var message = buildMessage(prompt, attachment);
        return createThreadAndRun(assistantType, message);
    }

    private Attachment buildAttachment(String fileId) {
        return Attachment.builder()
                .fileId(fileId)
                .addTool(Attachment.Tool.ofFileSearch())
                .build();
    }

    private Run createThreadAndRun(OpenAIAssistantType assistantType, ThreadCreateAndRunParams.Thread.Message message) {
        var schema = ResponseFormatJsonSchema.JsonSchema.builder()
                .name(assistantType.getName())
                .strict(true)
                .schema(assistantType.getSchema())
                .build();
        var thread = ThreadCreateAndRunParams.Thread.builder()
                .addMessage(message)
                .build();
        var createAndRunParams = ThreadCreateAndRunParams.builder()
                .assistantId(assistantType.getId())
                .thread(thread)
                .responseFormat(ResponseFormatJsonSchema.builder().jsonSchema(schema).build())
                .build();
        return openAIClient.beta()
                .threads()
                .createAndRun(createAndRunParams);
    }

    private ThreadCreateAndRunParams.Thread.Message buildMessage(String prompt, Attachment attachment) {
        return ThreadCreateAndRunParams.Thread.Message.builder()
                .content(prompt)
                .role(ThreadCreateAndRunParams.Thread.Message.Role.USER)
                .addAttachment(attachment)
                .build();
    }
}
