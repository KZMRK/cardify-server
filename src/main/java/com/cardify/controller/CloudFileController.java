package com.cardify.controller;

import com.cardify.model.response.CloudFileDto;
import com.cardify.service.CloudFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cloud-files")
@AllArgsConstructor
public class CloudFileController {

    private final CloudFileService cloudFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CloudFileDto createCloudFile(@RequestPart MultipartFile file) {
        return cloudFileService.createCloudFile(file);
    }
}
