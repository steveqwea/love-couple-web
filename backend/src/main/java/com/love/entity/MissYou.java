package com.love.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MissYou {
    private Long id;
    private String coupleCode;
    private Long userId;
    private String nickname;
    private LocalDate missDate;
    private Integer count;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
