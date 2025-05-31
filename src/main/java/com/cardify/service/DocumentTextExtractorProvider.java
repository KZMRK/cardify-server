package com.cardify.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentTextExtractorProvider {

    String extract(MultipartFile file, int startPage, int endPage);
}
