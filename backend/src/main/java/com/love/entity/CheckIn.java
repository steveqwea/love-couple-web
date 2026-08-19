package com.love.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CheckIn {
    private Long id;
    private String coupleCode;
    private Long userId;
    private String nickname;
    private String avatar;
    private String mood;
    private Integer loveDegree;
    private LocalDate checkDate;
    private LocalDateTime createTime;
}
