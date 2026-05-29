package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank
    private String phone;

    @NotBlank
    private String code;
}
