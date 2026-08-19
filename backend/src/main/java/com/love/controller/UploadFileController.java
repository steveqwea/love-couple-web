package com.love.controller;

import com.love.entity.PhotoFile;
import com.love.service.PhotoFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** 从数据库读取并返回上传的文件（照片、头像） */
@RestController
public class UploadFileController {

    private final PhotoFileService photoFileService;

    public UploadFileController(PhotoFileService photoFileService) {
        this.photoFileService = photoFileService;
    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        return serve("/uploads/" + fileName);
    }

    @GetMapping("/uploads/avatar/{fileName:.+}")
    public ResponseEntity<byte[]> serveAvatar(@PathVariable String fileName) {
        return serve("/uploads/avatar/" + fileName);
    }

    private ResponseEntity<byte[]> serve(String url) {
        PhotoFile file = photoFileService.findByUrl(url);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;
        try {
            type = MediaType.parseMediaType(file.getContentType());
        } catch (Exception ignored) {
        }
        headers.setContentType(type);
        headers.setCacheControl("public, max-age=86400");
        return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);
    }
}
