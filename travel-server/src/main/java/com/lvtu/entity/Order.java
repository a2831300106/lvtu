package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("orders")
public class Order extends BaseEntity {
    private String orderNo;
    private Long userId;
    private Long productId;
    private String productName;
    private LocalDate date;
    private String ticketType;
    private String ticketName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Long couponId;
    private String contactName;
    private String contactPhone;
    private String remark;
    private String status;
    private String payMethod;
    private String voucherCode;
}
