package com.love.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CoupleUser {
    private Long id;
    private String coupleCode;
    private String nickname;
    private String username;
    private String password;
    private String avatar;
    private Long partnerId;
    private LocalDateTime createTime;
}
