package com.cardify.service;

import com.cardify.model.property.CloudinaryProperties;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final CloudinaryProperties cloudinaryProperties;

    @SneakyThrows
    public String uploadFile(MultipartFile file, String filename) {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of(
                "public_id", filename,
                "folder", cloudinaryProperties.imagesPath())
        );
        return uploadResult.get("secure_url").toString();
    }

}
