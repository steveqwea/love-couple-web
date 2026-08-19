package com.love.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Photo {
    private Long id;
    private String coupleCode;
    private String photoUrl;
    private String remark;
    private LocalDateTime uploadTime;
}
