package com.cardify.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class CloudFileDto {

    private String id;

    private String filename;

    private String originalFilename;

    private String contentType;

    private Long fileSize;

    private String url;
}
