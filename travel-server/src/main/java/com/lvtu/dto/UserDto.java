package com.lvtu.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private Integer status;
}
