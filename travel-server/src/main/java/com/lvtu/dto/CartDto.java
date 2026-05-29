package com.lvtu.dto;

import lombok.Data;

@Data
public class CartDto {
    private Long productId;
    private String skuType;
    private Integer quantity;
}
