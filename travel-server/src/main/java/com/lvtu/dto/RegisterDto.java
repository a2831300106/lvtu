package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class RegisterDto {
    @NotBlank
    private String phone;

    @NotBlank
    private String code;

    private Boolean agreeTerms;
}
