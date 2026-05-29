package com.lvtu.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartVo {
    private Long productId;
    private String productName;
    private String skuType;
    private String skuName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
