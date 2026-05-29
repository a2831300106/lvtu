package com.lvtu.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserCouponVo {
    private Long id;
    private Long couponId;
    private String name;
    private BigDecimal amount;
    private String condition;
    private LocalDateTime expireTime;
    private Boolean used;
}
