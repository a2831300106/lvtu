package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class OrderPayDto {
    @NotBlank
    private String orderId;

    @NotBlank
    private String payMethod;
}
