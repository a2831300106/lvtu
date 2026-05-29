package com.lvtu.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SkuItemDto {
    private String name;
    private String type;
    private BigDecimal price;
    private Integer stock;
}
