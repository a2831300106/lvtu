package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class SendCodeDto {
    @NotBlank
    private String phone;

    @NotBlank
    private String type;
}
