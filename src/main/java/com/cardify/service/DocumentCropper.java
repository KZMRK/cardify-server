package com.cardify.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface DocumentCropper {

    OutputStream crop(MultipartFile file, int startPage, int endPage);
}
