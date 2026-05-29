package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lvtu.common.JwtUtils;
import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.dto.LoginDto;
import com.lvtu.dto.RegisterDto;
import com.lvtu.dto.SendCodeDto;
import com.lvtu.dto.UpdateUserDto;
import com.lvtu.entity.User;
import com.lvtu.mapper.UserMapper;
import com.lvtu.service.UserService;
import com.lvtu.vo.LoginVo;
import com.lvtu.vo.UserInfoVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<LoginVo> login(LoginDto dto) {
        if (!"123456".equals(dto.getCode())) {
            return Result.error("验证码错误");
        }
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getPhone, dto.getPhone())
        );
        if (user == null) {
            user = new User();
            user.setPhone(dto.getPhone());
            user.setNickname("用户" + dto.getPhone().substring(dto.getPhone().length() - 4));
            user.setLevel("bronze");
            user.setMemberSince(LocalDateTime.now());
            user.setStatus(1);
            userMapper.insert(user);
        }
        String token = jwtUtils.generateToken(user.getId(), "USER");
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(user.getId());
        userInfoVo.setPhone(user.getPhone());
        userInfoVo.setNickname(user.getNickname());
        userInfoVo.setAvatar(user.getAvatar());
        userInfoVo.setLevel(user.getLevel());
        userInfoVo.setMemberSince(user.getMemberSince());
        loginVo.setUserInfo(userInfoVo);
        return Result.success(loginVo);
    }

    @Override
    public Result<Void> register(RegisterDto dto) {
        if (!"123456".equals(dto.getCode())) {
            return Result.error("验证码错误");
        }
        User exist = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getPhone, dto.getPhone())
        );
        if (exist != null) {
            return Result.error("该手机号已注册");
        }
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setNickname("用户" + dto.getPhone().substring(dto.getPhone().length() - 4));
        user.setLevel("bronze");
        user.setMemberSince(LocalDateTime.now());
        user.setStatus(1);
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result<Void> sendCode(SendCodeDto dto) {
        return Result.success();
    }

    @Override
    public Result<UserInfoVo> getUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        UserInfoVo vo = new UserInfoVo();
        vo.setId(user.getId());
        vo.setPhone(user.getPhone());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setLevel(user.getLevel());
        vo.setMemberSince(user.getMemberSince());
        return Result.success(vo);
    }

    @Override
    public Result<Void> updateUser(UpdateUserDto dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        userMapper.updateById(user);
        return Result.success();
    }
}
