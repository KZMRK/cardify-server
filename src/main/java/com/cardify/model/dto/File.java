package com.cardify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
@AllArgsConstructor(staticName = "of")
public class File implements MultipartFile {

    private String name;
    private byte[] bytes;
    private String contentType;

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public boolean isEmpty() {
        return bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @NonNull
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(@NonNull java.io.File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(bytes, dest);
    }
}
