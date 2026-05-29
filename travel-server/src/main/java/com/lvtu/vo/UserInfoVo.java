package com.lvtu.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserInfoVo {
    private Long id;
    private String phone;
    private String nickname;
    private String avatar;
    private String level;
    private LocalDateTime memberSince;
}
