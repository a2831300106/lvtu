package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.dto.LoginDto;
import com.lvtu.dto.RegisterDto;
import com.lvtu.dto.SendCodeDto;
import com.lvtu.dto.UpdateUserDto;
import com.lvtu.vo.LoginVo;
import com.lvtu.vo.UserInfoVo;

public interface UserService {
    Result<LoginVo> login(LoginDto dto);
    Result<Void> register(RegisterDto dto);
    Result<Void> sendCode(SendCodeDto dto);
    Result<UserInfoVo> getUserInfo();
    Result<Void> updateUser(UpdateUserDto dto);
}
