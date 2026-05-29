package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.LoginDto;
import com.lvtu.dto.RegisterDto;
import com.lvtu.dto.SendCodeDto;
import com.lvtu.dto.UpdateUserDto;
import com.lvtu.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDto dto) {
        return userService.login(dto);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/sendCode")
    public Result<?> sendCode(@RequestBody SendCodeDto dto) {
        return userService.sendCode(dto);
    }

    @GetMapping("/info")
    public Result<?> info() {
        return userService.getUserInfo();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody UpdateUserDto dto) {
        return userService.updateUser(dto);
    }
}
