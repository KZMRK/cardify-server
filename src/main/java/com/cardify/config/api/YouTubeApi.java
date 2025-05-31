package com.cardify.config.api;

import com.cardify.model.response.YouTubeTranscriptResponse;
import feign.CollectionFormat;
import feign.Param;
import feign.RequestLine;

import java.net.URI;

public interface YouTubeApi {

    @RequestLine("GET /watch?v={videoId}")
    String getPageContent(@Param("videoId") String videoId);

    @RequestLine(value = "GET", collectionFormat = CollectionFormat.CSV)
    YouTubeTranscriptResponse getTranscript(URI uri);
}
