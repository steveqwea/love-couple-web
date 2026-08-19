package com.love.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Anniversary {
    private Long id;
    private String coupleCode;
    private String title;
    private LocalDate eventDate;
    private Integer type;
    private LocalDateTime createTime;
}
