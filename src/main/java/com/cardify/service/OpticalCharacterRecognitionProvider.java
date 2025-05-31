package com.cardify.service;

import org.springframework.web.multipart.MultipartFile;

public interface OpticalCharacterRecognitionProvider {

    String extractText(MultipartFile file);
}
