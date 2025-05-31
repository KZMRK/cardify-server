package com.cardify.service;

import com.cardify.mapper.CloudFileMapper;
import com.cardify.model.entity.CloudFile;
import com.cardify.model.entity.User;
import com.cardify.model.response.CloudFileDto;
import com.cardify.repository.CloudFileRepository;
import com.cardify.repository.UserRepository;
import com.cardify.security.CardifyContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CloudFileService {

    private final CloudFileRepository cloudFileRepository;
    private final CloudFileMapper cloudFileMapper;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;

    public CloudFileDto createCloudFile(MultipartFile file) {
        log.info("[createCloudFile] invoked with fileName=[{}]", file.getOriginalFilename());
        String filename = UUID.randomUUID().toString();
        String url = cloudinaryService.uploadFile(file, filename);
        CloudFile cloudFile = cloudFileMapper.toEntity(file, filename, url);
        cloudFile = cloudFileRepository.save(cloudFile);
        return cloudFileMapper.toDto(cloudFile);
    }

}
