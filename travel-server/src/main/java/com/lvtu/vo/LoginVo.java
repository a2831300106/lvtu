package com.lvtu.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private UserInfoVo userInfo;
}
