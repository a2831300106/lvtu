package com.lvtu.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderVo {
    private Long id;
    private String orderNo;
    private Long productId;
    private String productName;
    private LocalDate date;
    private String ticketName;
    private Integer quantity;
    private BigDecimal payAmount;
    private String status;
    private String contactName;
    private String contactPhone;
    private LocalDateTime createTime;
    private String voucherCode;
    private String remark;
}
