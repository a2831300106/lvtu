package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("coupon")
public class Coupon extends BaseEntity {
    private String name;
    private BigDecimal amount;
    private BigDecimal conditionAmount;
    private Integer total;
    private Integer received;
    private Integer expireDays;
    private Integer status;
}
