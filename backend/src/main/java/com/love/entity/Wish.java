package com.love.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Wish {
    private Long id;
    private String coupleCode;
    private String wisher;
    private String content;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
