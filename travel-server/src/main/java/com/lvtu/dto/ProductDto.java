package com.lvtu.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private String name;
    private Long categoryId;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer status;
    private List<SkuItemDto> skus;
}
