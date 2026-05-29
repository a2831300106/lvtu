package com.lvtu.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SkuVo {
    private Long id;
    private String name;
    private String type;
    private BigDecimal price;
    private Integer stock;
}
