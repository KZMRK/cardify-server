package com.cardify.mapper;

import com.cardify.model.entity.CloudFile;
import com.cardify.model.response.CloudFileDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudFileMapper {

    public CloudFile toEntity(MultipartFile file, String filename, String url) {
        CloudFile cloudFile = new CloudFile();
        cloudFile.setFilename(filename);
        cloudFile.setOriginalFilename(file.getOriginalFilename());
        cloudFile.setContentType(file.getContentType());
        cloudFile.setFileSize(file.getSize());
        cloudFile.setUrl(url);
        return cloudFile;
    }

    public CloudFileDto toDto(CloudFile entity) {
        if (entity == null) {
            return null;
        }
        return new CloudFileDto()
                .setId(entity.getId())
                .setFilename(entity.getFilename())
                .setOriginalFilename(entity.getOriginalFilename())
                .setContentType(entity.getContentType())
                .setFileSize(entity.getFileSize())
                .setUrl(entity.getUrl());

    }
}
