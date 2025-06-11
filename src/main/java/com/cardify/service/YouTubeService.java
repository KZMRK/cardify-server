package com.cardify.service;

import com.cardify.config.api.YouTubeApi;
import com.cardify.model.exception.BadRequestException;
import com.cardify.model.exception.CardifyException;
import com.cardify.model.response.YouTubeTranscriptResponse;
import com.cardify.model.type.ApiErrorStatusType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class YouTubeService {

    private static final Pattern pattern = Pattern.compile("(https://www.youtube.com/api/timedtext\\?.*?)\"");

    private final YouTubeApi youTubeApi;

    public YouTubeTranscriptResponse getVideoSubtitles(String videoId) {
        String pageContent = youTubeApi.getPageContent(videoId);
        Matcher matcher = pattern.matcher(pageContent);
        if (matcher.find()) {
            String encodedUrl = matcher.group(1).replace("\\u0026", "&");
            String captionUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
            log.info("Caption URL: {}", captionUrl);
            return youTubeApi.getTranscript(URI.create(captionUrl));
        } else {
            log.info("Without subtitles");
            throw new BadRequestException(ApiErrorStatusType.SUBTITLES_REQUIRED, "Відео без субтитрів");
        }
    }
}
