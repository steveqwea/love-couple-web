package com.love.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private String coupleCode;
    private String senderName;
    private String content;
    private LocalDateTime createTime;
}
