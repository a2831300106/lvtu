package com.lvtu.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailVo {
    private Long id;
    private String name;
    private List<String> images;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String description;
    private List<String> includes;
    private List<String> rules;
    private String openTime;
    private List<SkuVo> skus;
    private Long categoryId;
}
