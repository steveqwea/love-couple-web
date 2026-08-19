package com.love.entity;

import lombok.Data;
import java.time.LocalDateTime;

/** 上传文件的二进制内容，存数据库以保证云端部署时文件不丢失 */
@Data
public class PhotoFile {
    private Long id;
    private String url;
    private String contentType;
    private byte[] data;
    private LocalDateTime createTime;
}
