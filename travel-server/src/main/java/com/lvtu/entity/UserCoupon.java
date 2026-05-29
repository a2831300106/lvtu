package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_coupon")
public class UserCoupon extends BaseEntity {
    private Long userId;
    private Long couponId;
    private Boolean used;
    private LocalDateTime usedTime;
    private LocalDateTime expireTime;
}
