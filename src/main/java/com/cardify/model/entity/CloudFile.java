package com.cardify.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cloud_files")
public class CloudFile extends BaseEntity {

    @Column(name = "filename", unique = true)
    private String filename;

    @Column(name = "original_filename", length = 64)
    private String originalFilename;

    @Column(name = "content_type", length = 16)
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "url")
    private String url;
}
