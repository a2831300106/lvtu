package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderCreateDto {
    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String date;

    @NotBlank
    private String ticketType;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal unitPrice;

    private Long couponId;

    @NotBlank
    private String contactName;

    @NotBlank
    private String contactPhone;

    private String remark;
}
